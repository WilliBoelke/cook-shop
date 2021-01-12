package com.example.cookshop.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cookshop.R;
import com.example.cookshop.controller.network.BluetoothConnection;
import com.example.cookshop.controller.network.OnSyncFinishedCallback;
import com.example.cookshop.controller.network.SynchronizationManager;
import com.example.cookshop.items.Article;
import com.example.cookshop.controller.applicationController.ApplicationController;
import com.example.cookshop.view.adapter.DeviceListAdapter;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * This Activity lets the user Synchronize lists via Bluetooth
 * it will automatically activate Bluetooth and  Discoverability
 * in {@link #onCreate(Bundle)}
 *
 * The user the can pick a device from the list view by clicking on it
 * this will create a bond with that device and start the {@link SynchronizationManager}
 * and thus the {@link BluetoothConnection} in AcceptThread
 * (on both bonded devices) as soon as one of them clicks the connect button
 * it will call {@link SynchronizationManager}'s execute method. which will also start the BluetoothConnections connect
 * thread and automatically start the list synchronisation.
 *
 * @author WilliBoelke
 */
public class SynchronizeActivity extends AppCompatActivity
{


    //------------Instance Variables------------

    /**
     * The Log Tag
     */
    private final String TAG = this.getClass().getSimpleName();

    /**
     * The BluetoothAdapter
     */
    private BluetoothAdapter mBluetoothAdapter;

    /**
     * The BluetoothDevice which we
     * created a bond with
     */
    private BluetoothDevice bondedBluetoothDevice;

    /**
     * The SynchronizationManager which will synchronize the lists
     */
    private SynchronizationManager synchronizationManager;

    /**
     * The list of nearby bluetooth devices
     * which is displayed in the {@link #listView}
     *
     * Populated  in the {@link #foundDeviceReceiver}
     */
    private ArrayList<BluetoothDevice> mBTDevices;

    /**
     * ListAdapter to display {@link BluetoothDevice}
     * in {@link #listView}
     */
    private DeviceListAdapter mDeviceListAdapter;

    /**
     * ListView to show discovered BluetoothDevices
     */
    private ListView listView;

    /**
     * TextView to change Synchronization updates to the user
     */
    private TextView msgTextView;

    /**
     * Button to start ty syc process after bonded
     * to another device
     */
    private Button connect;

    /**
     * ProgressBar to show thy sync progress to the user
     * (not working yet)
     */
    private ProgressBar progressBar;


    //------------Activity/Fragment Lifecycle------------


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronie);

        listView = findViewById(R.id.device_list_view);
        mBTDevices = new ArrayList<>();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        connect = findViewById(R.id.connect_button);
        msgTextView = findViewById(R.id.status_text_view);
        listView.setOnItemClickListener(this.listClickListener);
        progressBar = findViewById(R.id.progress_par);
        //Broadcasts whenBond state changes, pairing devices for example
        IntentFilter bondStateChangeFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(bondStateChangedReceiver, bondStateChangeFilter);

        enableBluetooth();
        makeDiscoverable();
        startDiscovery();

    }


    private void startSyncManager()
    {
        Log.d(TAG, "startSynchronization: starting synchronization manager");
        synchronizationManager= new SynchronizationManager(new BluetoothConnection(mBluetoothAdapter), bondedBluetoothDevice, new OnSyncFinishedCallback<Article>()
        {
            @Override
            public void onSyncFinished(ArrayList<Article> syncedList, String result)
            {
                progressBar.setVisibility(View.INVISIBLE);
                msgTextView.setText("The lists are synchronized");
            }
        }, ApplicationController.getInstance());

    }


    public void  startSynchronization(View view)
    {
        if(synchronizationManager != null)
        {
            connect.setClickable(false); // not clickable again
            connect.setVisibility(View.INVISIBLE);
            synchronizationManager.execute();
        }
        else
        {
            Log.e(TAG, "startSynchronization: Sync not ready yet, bond with device first");
            msgTextView.setText("Sync not ready yet, bond with device first");
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        try
        {
            unregisterReceiver(actionStateChangedReceiver);
        }
        catch (IllegalArgumentException e)
        {
            //receiver was not registered
            Log.e(TAG, "onDestroy: actionStateChangedReceiver was not registered ");
        }
        try
        {
            unregisterReceiver(actionScanModeChangedReceiver);
        }
        catch (IllegalArgumentException e)
        {
            //receiver was not registered
            Log.e(TAG, "onDestroy: actionScanModeChangedReceiver was not registered ");
        }
        try
        {
            unregisterReceiver(foundDeviceReceiver);
        }
        catch (IllegalArgumentException e)
        {
            //receiver was not registered
            Log.e(TAG, "onDestroy: foundDeviceReceiver was not registered ");
        }
        try
        {
            unregisterReceiver(bondStateChangedReceiver);
        }
        catch (IllegalArgumentException e)
        {
            //receiver was not registered
            Log.e(TAG, "onDestroy: bondStateChangedReceiver was not registered ");
        }
        try {
            Method m = bondedBluetoothDevice.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(bondedBluetoothDevice, (Object[]) null);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }





    //------------Enabling Bluetooth------------


    /**
     * Enables bluetooth if its not enabled yet.
     */
    private void enableBluetooth()
    {
        Log.d(TAG, "enableBluetooth: enabling Bluetooth");
        if(mBluetoothAdapter == null)
        {
            Log.e(TAG, "This Device does not support Bluetooth") ;
        }
        else if (!mBluetoothAdapter.isEnabled())
        {
            // Enable Bluetooth
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBluetoothIntent);

            //Register Broadcast Receiver

            IntentFilter BluetoothIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(actionStateChangedReceiver, BluetoothIntent);
        }
    }


    /**
     * makes the device discoverable for other bluetooth devices
     */
    private void makeDiscoverable()
    {
        Log.d(TAG, "makeDiscoverable: making device discoverable");
        //Discoverable Intent
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 500);
        startActivity(discoverableIntent);

        //Register Broadcast Receiver
        IntentFilter intentFilter = new IntentFilter(mBluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(actionScanModeChangedReceiver, intentFilter);
    }


    /**
     * starts looking for other devices
     */
    private void startDiscovery()
    {
        Log.d(TAG, "startDiscovery: start looking for other devices");

        if(mBluetoothAdapter.isDiscovering())
        {
            Log.d(TAG, "makeDiscoverable: malready scanning, restarting ... ");
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothAdapter.startDiscovery();

            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(foundDeviceReceiver, discoverDevicesIntent);
        }
        if(!mBluetoothAdapter.isDiscovering())
        {
            mBluetoothAdapter.startDiscovery();

            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(foundDeviceReceiver, discoverDevicesIntent);
        }
    }





    //------------Bluetooth BroadcastReceiver-----------


    /**
     * BroadcastReceiver for Bluetooth ACTION_STATE_CHANGED
     * (mainly for logging at this moment)
     */
    private final BroadcastReceiver actionStateChangedReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED))
            {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch (state)
                {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };


    /**
     * BroadcastReceiver for Bluetooth ACTION_SCAN_MODE_CHANGED
     * (mainly for logging at this moment)
     */
    private final BroadcastReceiver actionScanModeChangedReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED))
            {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode)
                {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }
            }
        }
    };



    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    private BroadcastReceiver foundDeviceReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_FOUND))
            {
                //Getting  new BTDevice from intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG, "mBroadcastReceiver3: onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.recycler_card_device, mBTDevices);
                listView.setAdapter(mDeviceListAdapter);
            }
        }
    };



    /**
     * Broadcast Receiver to react to  bond state changes (Pairing status changes)
     */
    private final BroadcastReceiver bondStateChangedReceiver = new BroadcastReceiver()
    {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent)
        {
            final String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED))
            {
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED)
                {
                    Log.d(TAG, "mBroadcastReceiver4: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    bondedBluetoothDevice = mDevice;
                    listView.setVisibility(View.INVISIBLE);
                    msgTextView.setVisibility(View.VISIBLE);
                    connect.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.primary_dark));
                    msgTextView.setText("Click Synchronization to proceed ");
                    startSyncManager();
                }
                //case2: creating a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING)
                {
                    Log.d(TAG, "mBroadcastReceiver4: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE)
                {
                    Log.d(TAG, "mBroadcastReceiver4: BOND_NONE.");
                }
            }
        }
    };

    private AdapterView.OnItemClickListener  listClickListener=  new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mBluetoothAdapter.cancelDiscovery();

            Log.d(TAG, "Clicked on ListViewItem at  position " + position );
            String deviceName = mBTDevices.get(position).getName();
            String deviceAddress = mBTDevices.get(position).getAddress();
            Log.d(TAG, "Clicked on ListViewItem with name :  " + deviceName + " " + deviceAddress);
            mBTDevices.get(position).createBond();
        }
    };
}

package com.example.cookshop.view.main;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ListView;

import com.example.cookshop.R;
import com.example.cookshop.view.recyclerViews.DeviceListAdapter;

import java.util.ArrayList;

public class SynchronizeActivity extends AppCompatActivity
{
    private final String TAG = this.getClass().getSimpleName();
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice bondedBluetoothDevice;
    private ArrayList<BluetoothDevice> mBTDevices;
    private DeviceListAdapter mDeviceListAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronie);

        listView = findViewById(R.id.device_list_view);
        mBTDevices = new ArrayList<>();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
     listView.setOnItemClickListener(this.listClickListener);
        //Broadcasts whenBond state changes, pairing devices for example
        IntentFilter bondStateChangeFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, bondStateChangeFilter);


        enableBluetooth();
        makeDiscoverable();
        startDiscovery();
    }


    @Override
    protected void onDestroy()
    {
      super.onDestroy();
      try
      {
          unregisterReceiver(mBroadcastReceiver1);
      }
      catch (IllegalArgumentException e)
      {
          //receiver was not registered
          Log.e(TAG, "onDestroy: Receiver1 was not registered ");
      }
        try
        {
            unregisterReceiver(mBroadcastReceiver2);
        }
        catch (IllegalArgumentException e)
        {
            //receiver was not registered
            Log.e(TAG, "onDestroy: Receiver2 was not registered ");
        }
        try
        {
            unregisterReceiver(mBroadcastReceiver3);
        }
        catch (IllegalArgumentException e)
        {
            //receiver was not registered
            Log.e(TAG, "onDestroy: Receiver3 was not registered ");
        }
        try
        {
            unregisterReceiver(mBroadcastReceiver4);
        }
        catch (IllegalArgumentException e)
        {
            //receiver was not registered
            Log.e(TAG, "onDestroy: Receiver4 was not registered ");
        }
    }


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
            registerReceiver(mBroadcastReceiver1, BluetoothIntent);
        }
    }


    private void makeDiscoverable()
    {
        Log.d(TAG, "makeDiscoverable: making device discoverable");
        //Discoverable Intent
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 500);
        startActivity(discoverableIntent);

        //Register Broadcast Receiver

        IntentFilter intentFilter = new IntentFilter(mBluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver2, intentFilter);

    }


    private void startDiscovery()
    {
        Log.d(TAG, "startDiscovery: start looking for other devices");

        if(mBluetoothAdapter.isDiscovering())
        {
            Log.d(TAG, "makeDiscoverable: malready scanning, restarting ... ");
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothAdapter.startDiscovery();

            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if(!mBluetoothAdapter.isDiscovering())
        {
            mBluetoothAdapter.startDiscovery();

            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }




    /**
     * BroadcastReceiver for Bluetooth ACTION_STATE_CHANGED
     * (mainly for logging at this moment)
     */
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver()
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
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver()
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
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver()
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
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver()
    {
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
                }
                //case2: creating a bone
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
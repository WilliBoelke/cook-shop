package cookshop.network;


import android.bluetooth.BluetoothDevice;

import com.example.cookshop.controller.network.BluetoothConnection;
import com.example.cookshop.controller.network.OnReceiveCallback;
import com.example.cookshop.controller.network.SynchronizationManager;
import com.example.cookshop.model.listManagement.DataAccess;


import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class SynchronizationManagerTest
{

    private BluetoothConnection mockBtConnection;
    private BluetoothDevice mockBtDevice;
    private DataAccess mockDataAccess;
    private SynchronizationManager testSyncManager;


    @Before
    public void setup()
    {
        mockBtDevice = mock(BluetoothDevice.class);
        mockDataAccess = mock(DataAccess.class);
        mockBtConnection = mock(BluetoothConnection.class);
    }


    private OnReceiveCallback notNeededReceiveCallback = new OnReceiveCallback()
    {
        @Override
        public void onIncomingMessage(String Message)
        {
            // using that if i dont need one , if i need one it will be implemented in the test itself
        }
    };

    @Test
    public void setOnReceiveListenerTest()
    {

        testSyncManager = new SynchronizationManager(mockBtConnection, mockBtDevice, notNeededReceiveCallback, mockDataAccess );

        verify(mockBtConnection).setOnReceiveListener(notNeededReceiveCallback);
    }



}
package cookshop.network;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import com.example.cookshop.controller.network.BluetoothConnection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

public class BluetoothConnectionTest
{

    /**
    private Context mockContext;
    private BluetoothAdapter mockAdapter;
    private BluetoothServerSocket mockServerSocket;
    private BluetoothSocket mockBluetoothSocket;
    private InputStream mockInputStream;
    private OutputStream mockOutpuStream;
    private BluetoothConnection bluetoothConnection;
    private BluetoothDevice mockBluetoothDevice;
    private  final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    @Before
    public void setUp() throws Exception
    {
        mockContext = Mockito.mock(Context.class);
        mockAdapter = Mockito.mock(BluetoothAdapter.class);
        mockServerSocket = Mockito.mock(BluetoothServerSocket.class);
        mockBluetoothSocket = Mockito.mock(BluetoothSocket.class);
        mockInputStream = Mockito.mock(InputStream.class);
        mockOutpuStream = Mockito.mock(OutputStream.class);
        mockBluetoothDevice = Mockito.mock(BluetoothDevice.class);


        bluetoothConnection = new BluetoothConnection( mockAdapter);

        //
        Mockito.when(mockAdapter.listenUsingInsecureRfcommWithServiceRecord(Mockito.anyString(), any(UUID.class))).thenReturn(mockServerSocket);
        Mockito.when(mockServerSocket.accept()).thenReturn(mockBluetoothSocket);
        Mockito.when(mockBluetoothSocket.getInputStream()).thenReturn(mockInputStream);
        Mockito.when(mockBluetoothSocket.getOutputStream()).thenReturn(mockOutpuStream);
        Mockito.when(mockBluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID_INSECURE)).thenReturn(mockBluetoothSocket);
    }

    @Test
    public void verifyMethodCallsInAcceptThread() throws Exception
    {
        bluetoothConnection.startServer();
        Mockito.verify(mockAdapter).listenUsingInsecureRfcommWithServiceRecord(Mockito.anyString(), any(UUID.class));
        Mockito.verify(mockServerSocket).accept();
    }

    @Test
    public void verifyMethodCallsInConnectedThread() throws Exception
    {


    }

    @Test
    public void verifyMethodCallsInConnectThread() throws Exception
    {
        bluetoothConnection.startClient(mockBluetoothDevice, MY_UUID_INSECURE);
        Mockito.verify(mockBluetoothDevice).createRfcommSocketToServiceRecord(any(UUID.class));
        Mockito.verify(mockBluetoothSocket).connect();
    }

    **/
}
package cookshop.network;

import android.bluetooth.BluetoothDevice;

import com.example.cookshop.controller.network.NetworkConnection;
import com.example.cookshop.controller.network.OnReceiveCallback;
import com.example.cookshop.items.Article;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Used for dependency injection in {@link SynchronizationManagerTest}
 */
public class MockNetworkConnectionServer implements NetworkConnection
{
    private final ArrayList<Article> receivedArticles;
    private OnReceiveCallback listener;
    private SendThread sendThread;
    private ReceiveThread receiveThread;


    public MockNetworkConnectionServer()
    {
        this.receivedArticles = new ArrayList<>();
    }

    @Override
    public void startServer()
    {

    }

    @Override
    public void startClient(BluetoothDevice device, UUID uuid)
    {

    }

    @Override
    public void write(String message)
    {
        if (message.equals("FIN"))
        {
            this.sendThread = new SendThread();
            sendThread.start();
            return;
        }
        Article article = new Article(message);
        this.receivedArticles.add(article);
        this.receiveThread = new ReceiveThread();
        receiveThread.start();
    }

    @Override
    public void setOnReceiveListener(OnReceiveCallback listener)
    {
        this.listener = listener;

    }

    @Override
    public boolean isServer()
    {
        return true;
    }

    @Override
    public boolean isConnected()
    {
        return true;
    }

    @Override
    public void closeConnection()
    {

    }


    public ArrayList<Article> getReceivedArticles()
    {
        return receivedArticles;
    }


    private class ReceiveThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                synchronized (this)
                {
                    this.wait(100);
                }
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            listener.onIncomingMessage("ACK");
        }
    }


    private class SendThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                synchronized (this)
                {
                    this.wait(100);
                }
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            listener.onIncomingMessage("FIN");
        }
    }


}

package cookshop.network;

import android.bluetooth.BluetoothDevice;

import com.example.cookshop.controller.network.NetworkConnection;
import com.example.cookshop.controller.network.OnReceiveCallback;
import com.example.cookshop.controller.network.SynchronizationManager;
import com.example.cookshop.items.Article;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.UUID;

import kotlinx.coroutines.channels.Send;

public class MockNetworkConnectionClient implements NetworkConnection
{
    private ArrayList<Article> receivedArticles;
    private ArrayList<Article> sendArticles;
    private OnReceiveCallback listener;
    private SendThread sendThread;
    private ReceiveThread receiveThread;


    public MockNetworkConnectionClient(ArrayList sendArticles)
    {
        this.sendArticles = sendArticles;
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
            System.out.println("Get FIN ... ");
            return;
        }
        if (message.equals("ACK"))
        {
            return;
        }
        Article article = new Article();
        article.setObjectFromMementoPattern(message);
        this.receivedArticles.add(article);
        this.receiveThread = new ReceiveThread();
        receiveThread.start();
    }
    int count = 0;



    @Override
    public void setOnReceiveListener(OnReceiveCallback listener)
    {
             this.listener = listener;
             count ++;
             if(count == 2)
             {
                 sendThread = new SendThread();
                 System.out.println("Start send thread ");
                 sendThread.start();
             }
    }

    @Override
    public boolean isServer()
    {
        return false;
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
                    this.wait(50);
                }
            }
                catch (InterruptedException e)
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
            for (Article a : sendArticles)
            {
                System.out.println("Sending ... ");
                listener.onIncomingMessage(a.getMementoPattern());
                try
                {
                    synchronized (this)
                    {
                        this.wait(1000);
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            listener.onIncomingMessage("FIN");
        }
    }
}

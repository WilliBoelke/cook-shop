package cookshop.network;


import android.bluetooth.BluetoothDevice;

import com.example.cookshop.controller.network.BluetoothConnection;
import com.example.cookshop.controller.network.OnReceiveCallback;
import com.example.cookshop.controller.network.SynchronizationManager;
import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.model.listManagement.DataAccess;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static kotlin.text.Typography.times;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class SynchronizationManagerTest
{

    private MockNetworkConnectionServer mockNetworkConnection;
    private BluetoothConnection mockBtConnection;
    private BluetoothDevice mockBtDevice;
    private DataAccess mockDataAccess;
    private SynchronizationManager testSyncManager;

    private Article testArticle1;
    private Article testArticle2;
    private Article testArticle3;

    private ArrayList<Article> testArticleArrayList1;
    private ArrayList<Article> testArticleArrayList2;
    private ArrayList<Article> testArticleArrayList3;

    @Before
    public void setUp() throws Exception
    {
         Date date = Calendar.getInstance().getTime();

         testArticle1 = new Article("Apfel", "4 Äpfel", Category.FRUIT, 4, 1.0, date, date);
         testArticle2 = new Article("Birne", "3 Birnen", Category.FRUIT, 3, 1.0,date, date);
         testArticle3 = new Article("Gurke", "Beschreibung", Category.VEGETABLE, 1, 13, date,date);
        testArticleArrayList1 = new ArrayList<>();
        testArticleArrayList1.add(testArticle1);
        testArticleArrayList1.add(testArticle2);
        testArticleArrayList1.add(testArticle3);

        testArticleArrayList2 = new ArrayList<>();
        testArticleArrayList2.add(testArticle1);
        testArticleArrayList2.add(testArticle2);

        testArticleArrayList3 = new ArrayList<>();
        testArticleArrayList3.add(testArticle3);

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


    /**
     * An OnReceive Callback should be set to the BTConnection
     * in the SynchronisationManagers Constructor
     */
    @Test
    public void setOnReceiveListenerTest()
    {
        //TestSetup

        testSyncManager = new SynchronizationManager(mockBtConnection, mockBtDevice, notNeededReceiveCallback, mockDataAccess );

        // Verifying Results

        verify(mockBtConnection).setOnReceiveListener(any(OnReceiveCallback.class));
    }


    /**
     * The BtConnections .startServer method should be called in the Constructor
     */
    @Test
    public void startServerTest()
    {
        //TestSetup

        testSyncManager = new SynchronizationManager(mockBtConnection, mockBtDevice, notNeededReceiveCallback, mockDataAccess );

        // Verifying Results

        verify(mockBtConnection).startServer();
    }


    /**
     * Starting as Server
     * We send first (the test Articles)
     * all 3 test Articles should be send to the NetWorkConnection
     *
     * our own list should be overridden completely with the received list
     */
    @Test
    public void methodCalls()
    {
        //TestSetup
        when(mockDataAccess.getBuyingList()).thenReturn(testArticleArrayList1);
        mockNetworkConnection = new MockNetworkConnectionServer( );
        testSyncManager = new SynchronizationManager(mockNetworkConnection, mockBtDevice, notNeededReceiveCallback, mockDataAccess );
        testSyncManager.doInBackground();

        // Verifying Results
        verify(mockDataAccess).getBuyingList();
        verify(mockDataAccess).overrideShoppingListCompletely(any(ArrayList.class));
        Assert.assertTrue(testArticleArrayList1.size() == mockNetworkConnection.getReceivedArticles().size());

    }


    /**
     * Starting as Server
     * We send first (the test Articles)
     * all 3 test Articles should be send to the NetWorkConnection
     *
     * our own list should be overridden completely with the received list
     */
    @Test
    public void startAsServer()
    {
        //TestSetup
        when(mockDataAccess.getBuyingList()).thenReturn(testArticleArrayList1);
        mockNetworkConnection = new MockNetworkConnectionServer( );
        testSyncManager = new SynchronizationManager(mockNetworkConnection, mockBtDevice, notNeededReceiveCallback, mockDataAccess );
        testSyncManager.doInBackground();

        // Verifying Results
        verify(mockDataAccess).getBuyingList();
        verify(mockDataAccess).overrideShoppingListCompletely(any(ArrayList.class));
        Assert.assertTrue(testArticleArrayList1.size() == mockNetworkConnection.getReceivedArticles().size());

    }



    /**
     * Starting as Server
     * We send first (the test Articles)
     * all 3 test Articles should be send to the NetWorkConnection
     *
     * our own list should be overridden completely with the received list
     */
    @Test
    public void startAsClient1()
    {
        //TestSetup
        when(mockDataAccess.getBuyingList()).thenReturn(testArticleArrayList2);
        MockNetworkConnectionClient mockNetworkConnectionClient = new MockNetworkConnectionClient(testArticleArrayList3);
        testSyncManager = new SynchronizationManager(mockNetworkConnectionClient, mockBtDevice, notNeededReceiveCallback, mockDataAccess );
        testSyncManager.doInBackground();

        // Verifying Results
        verify(mockDataAccess, times(6)).getBuyingList();
        verify(mockDataAccess, times(1)).addArticleToShoppingList(any(Article.class));
        verify(mockDataAccess, times(0)).deleteArticleShoppingList(anyInt());
        Assert.assertTrue(2 == mockNetworkConnectionClient.getReceivedArticles().size());

    }


    /**
     * Same list, the synchronization should not make any additions or deletions
     */
    @Test
    public void startAsClient2()
    {
        //TestSetup
        when(mockDataAccess.getBuyingList()).thenReturn(testArticleArrayList2);
        MockNetworkConnectionClient mockNetworkConnectionClient = new MockNetworkConnectionClient(testArticleArrayList2);
        testSyncManager = new SynchronizationManager(mockNetworkConnectionClient, mockBtDevice, notNeededReceiveCallback, mockDataAccess );
        testSyncManager.doInBackground();

        // Verifying Results
        verify(mockDataAccess, atLeast(1)).getBuyingList();
        verify(mockDataAccess, times(0)).addArticleToShoppingList(any(Article.class));
        verify(mockDataAccess, times(0)).deleteArticleShoppingList(anyInt());
        Assert.assertTrue(2 == mockNetworkConnectionClient.getReceivedArticles().size());

    }


}
package cookshop.network;


import android.bluetooth.BluetoothDevice;

import com.example.cookshop.controller.applicationController.ApplicationController;
import com.example.cookshop.controller.network.BluetoothConnection;
import com.example.cookshop.controller.network.OnReceiveCallback;
import com.example.cookshop.controller.network.OnSyncFinishedCallback;
import com.example.cookshop.controller.network.SynchronizationManager;
import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the {@link SynchronizationManager}
 * The {@link BluetoothConnection} is replaces here with mock implementations
 * of {@link com.example.cookshop.controller.network.NetworkConnection} interface
 */
public class SynchronizationManagerTest
{

    private MockNetworkConnectionServer mockNetworkConnection;
    private BluetoothConnection mockBtConnection;
    private BluetoothDevice mockBtDevice;
    private ApplicationController mockApplicationController;
    private SynchronizationManager testSyncManager;

    private Article testArticle1;
    private Article testArticle2;
    private Article testArticle3;

    private ArrayList<Article> testArticleArrayList1;
    private ArrayList<Article> testArticleArrayList2;
    private ArrayList<Article> testArticleArrayList3;

    private final String dateString = "09-1-2021 07:50:40";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    private Date date;


    @Before
    public void setUp() throws Exception
    {
        date = simpleDateFormat.parse(dateString);
        testArticle1 = new Article("Apfel", "4 Äpfel", Category.FRUIT, 4, 1.0, date, date);
        testArticle2 = new Article("Birne", "3 Birnen", Category.FRUIT, 3, 1.0, date, date);
        testArticle3 = new Article("Gurke", "Beschreibung", Category.VEGETABLE, 1, 13, date, date);
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
        mockApplicationController = mock(ApplicationController.class);
        mockBtConnection = mock(BluetoothConnection.class);
    }


    private final OnSyncFinishedCallback<Article> notNeededReceiveCallback = new OnSyncFinishedCallback()
    {
        @Override
        public void onSyncFinished(ArrayList syncedList, String result)
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

        testSyncManager = new SynchronizationManager(mockBtConnection, mockBtDevice, notNeededReceiveCallback, mockApplicationController);

        // Verifying Results

        verify(mockBtConnection).setOnReceiveListener(any(OnReceiveCallback.class));
    }


    /**
     * The BtConnections .startServer method should be called in the Constructor
     */
    @Test
    public void startServer1()
    {
        //TestSetup

        testSyncManager = new SynchronizationManager(mockBtConnection, mockBtDevice, notNeededReceiveCallback, mockApplicationController);

        // Verifying Results

        verify(mockBtConnection).startServer();
    }


    /**
     * Starting as Server
     * We send first (the test Articles)
     * all 3 test Articles should be send to the NetWorkConnection
     * <p>
     * our own list should be overridden completely with the received list
     */
    @Test
    public void methodCalls()
    {
        //TestSetup
        when(mockApplicationController.getShoppingList()).thenReturn(testArticleArrayList1);
        mockNetworkConnection = new MockNetworkConnectionServer();
        testSyncManager = new SynchronizationManager(mockNetworkConnection, mockBtDevice, notNeededReceiveCallback, mockApplicationController);
        testSyncManager.doInBackground();

        // Verifying Results
        verify(mockApplicationController).getShoppingList();
        verify(mockApplicationController).overrideShoppingListCompletely(any(ArrayList.class));
        Assert.assertTrue(testArticleArrayList1.size() == mockNetworkConnection.getReceivedArticles().size());

    }


    /**
     * Starting as Server
     * We send first (the test Articles)
     * All 3 test Articles should be send to the NetworkConnection
     * our own list should be overridden completely with the received list
     */
    @Test
    public void startAsServer()
    {
        //TestSetup
        when(mockApplicationController.getShoppingList()).thenReturn(testArticleArrayList1);
        mockNetworkConnection = new MockNetworkConnectionServer();
        testSyncManager = new SynchronizationManager(mockNetworkConnection, mockBtDevice, notNeededReceiveCallback, mockApplicationController);
        testSyncManager.doInBackground();

        // Verifying Results
        verify(mockApplicationController).getShoppingList();
        verify(mockApplicationController).overrideShoppingListCompletely(any(ArrayList.class));
        Assert.assertTrue(testArticleArrayList1.size() == mockNetworkConnection.getReceivedArticles().size());

    }


    /**
     * Starting as the Server.
     * Just testing the "fist stage" here, the client wont return anything
     * (that will be tested in the next tests)
     */
    @Test
    public void startAsClient1()
    {
        //TestSetup
        when(mockApplicationController.getShoppingList()).thenReturn(testArticleArrayList2);
        MockNetworkConnectionClient mockNetworkConnectionClient = new MockNetworkConnectionClient(testArticleArrayList3);
        testSyncManager = new SynchronizationManager(mockNetworkConnectionClient, mockBtDevice, notNeededReceiveCallback, mockApplicationController);
        testSyncManager.doInBackground();

        // Verifying Results
        verify(mockApplicationController, times(6)).getShoppingList();
        verify(mockApplicationController, times(1)).addArticleToShoppingList(any(Article.class));
        verify(mockApplicationController, times(0)).deleteArticleFromShoppingList(anyInt());
        Assert.assertTrue(2 == mockNetworkConnectionClient.getReceivedArticles().size());

    }


    /**
     * Same list, the synchronization should not make any additions or deletions
     */
    @Test
    public void startAsClient2()
    {
        //TestSetup
        date = Calendar.getInstance().getTime();
        testArticle1 = new Article("Apfel", "4 Äpfel", Category.FRUIT, 4, 1.0, date, date);
        testArticle2 = new Article("Birne", "3 Birnen", Category.FRUIT, 3, 1.0, date, date);
        testArticleArrayList2 = new ArrayList<>();
        testArticleArrayList2.add(testArticle1);
        testArticleArrayList2.add(testArticle2);


        when(mockApplicationController.getShoppingList()).thenReturn(testArticleArrayList2);
        MockNetworkConnectionClient mockNetworkConnectionClient = new MockNetworkConnectionClient(testArticleArrayList2);
        testSyncManager = new SynchronizationManager(mockNetworkConnectionClient, mockBtDevice, notNeededReceiveCallback, mockApplicationController);
        testSyncManager.doInBackground();

        // Verifying Results
        verify(mockApplicationController, atLeast(1)).getShoppingList();
        verify(mockApplicationController, times(0)).addArticleToShoppingList(any(Article.class));
        verify(mockApplicationController, times(0)).deleteArticleFromShoppingList(anyInt());
        Assert.assertTrue(2 == mockNetworkConnectionClient.getReceivedArticles().size());

    }


    /**
     * Articles whit newer lastUpdateDates should be saved and the older ones should be deleted
     */
    @Test
    public void verifyUpdatedArticles() throws ParseException
    {
        //TestSetup

        ArgumentCaptor<Article> articleCaptor = ArgumentCaptor.forClass(Article.class);

        //changing one article description and update date
        Date newDate = simpleDateFormat.parse("10-1-2021 07:50:40");
        Article updatedTestArticle3 = new Article("Gurke", "neue beschreibung", Category.VEGETABLE, 1, 13, date, newDate);

        //Setting up 2 ArrayLists and changing article 3 in one of them
        testArticleArrayList1 = new ArrayList<>();
        testArticleArrayList1.add(testArticle1);
        testArticleArrayList1.add(testArticle2);
        testArticleArrayList1.add(testArticle3);
        testArticleArrayList2 = new ArrayList<>();
        testArticleArrayList2.add(testArticle1);
        testArticleArrayList2.add(testArticle2);
        testArticleArrayList2.add(updatedTestArticle3);

        //Injecting both lists
        when(mockApplicationController.getShoppingList()).thenReturn(testArticleArrayList1);
        MockNetworkConnectionClient mockNetworkConnectionClient = new MockNetworkConnectionClient(testArticleArrayList2);

        //Run
        testSyncManager = new SynchronizationManager(mockNetworkConnectionClient, mockBtDevice, notNeededReceiveCallback, mockApplicationController);
        testSyncManager.doInBackground();

        // Verifying Results
        verify(mockApplicationController, atLeast(1)).getShoppingList();
        verify(mockApplicationController, times(1)).addArticleToShoppingList(articleCaptor.capture());

        List<Article> savedArticles = articleCaptor.getAllValues();

        //verify that the newest Article will be stored and n ot the older version
        Assert.assertEquals(savedArticles.get(0).getName(), updatedTestArticle3.getName());
        Assert.assertEquals(savedArticles.get(0).getDateOfUpdate(), updatedTestArticle3.getDateOfUpdate());

    }


    /**
     * Articles whit newer lastUpdateDates should be saved and the older ones should be deleted
     */
    @Test
    public void verifyThatOldArticleWillBeDeleted() throws ParseException
    {
        //TestSetup

        ArgumentCaptor<Article> articleCaptor = ArgumentCaptor.forClass(Article.class);

        //changing one article description and update date
        Date newDate = simpleDateFormat.parse("10-1-2021 07:50:40");
        Article updatedTestArticle3 = new Article("Gurke", "neue beschreibung", Category.VEGETABLE, 1, 13, date, newDate);

        //Setting up 2 ArrayLists and changing article 3 in one of them
        testArticleArrayList1 = new ArrayList<>();
        testArticleArrayList1.add(testArticle1);
        testArticleArrayList1.add(testArticle2);
        testArticleArrayList1.add(testArticle3);
        testArticleArrayList2 = new ArrayList<>();
        testArticleArrayList2.add(testArticle1);
        testArticleArrayList2.add(testArticle2);
        testArticleArrayList2.add(updatedTestArticle3);

        //Injecting both lists
        when(mockApplicationController.getShoppingList()).thenReturn(testArticleArrayList1);
        MockNetworkConnectionClient mockNetworkConnectionClient = new MockNetworkConnectionClient(testArticleArrayList2);

        //Run
        testSyncManager = new SynchronizationManager(mockNetworkConnectionClient, mockBtDevice, notNeededReceiveCallback, mockApplicationController);
        testSyncManager.doInBackground();

        // Verifying Results
        verify(mockApplicationController, atLeast(1)).getShoppingList();
        verify(mockApplicationController, times(1)).deleteArticleFromShoppingList(2); // old testArticle3 is at index 2

    }


    /**
     * Now, we have the newest venison of the article in "our" list
     * no articles should be added or deleted
     */
    @Test
    public void noChangesWhenNoNewerArticles() throws ParseException
    {
        //TestSetup
        //changing one article description and update date
        Date newDate = simpleDateFormat.parse("10-1-2021 07:50:40");
        Article updatedTestArticle3 = new Article("Gurke", "neue beschreibung", Category.VEGETABLE, 1, 13, date, newDate);

        //Setting up 2 ArrayLists and changing article 3 in one of them
        testArticleArrayList1 = new ArrayList<>();
        testArticleArrayList1.add(testArticle1);
        testArticleArrayList1.add(testArticle2);
        testArticleArrayList1.add(testArticle3);
        testArticleArrayList2 = new ArrayList<>();
        testArticleArrayList2.add(testArticle1);
        testArticleArrayList2.add(testArticle2);
        testArticleArrayList2.add(updatedTestArticle3);

        //Injecting both lists
        when(mockApplicationController.getShoppingList()).thenReturn(testArticleArrayList2);
        MockNetworkConnectionClient mockNetworkConnectionClient = new MockNetworkConnectionClient(testArticleArrayList1);

        //Run
        testSyncManager = new SynchronizationManager(mockNetworkConnectionClient, mockBtDevice, notNeededReceiveCallback, mockApplicationController);
        testSyncManager.doInBackground();

        // Verifying Results
        verify(mockApplicationController, atLeast(1)).getShoppingList();
        verify(mockApplicationController, times(0)).addArticleToShoppingList(any(Article.class));
        verify(mockApplicationController, times(0)).deleteArticleFromShoppingList(anyInt());


    }


}

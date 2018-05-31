package com.upwork.eas;

import org.junit.*;
import org.junit.runners.MethodSorters;

/**
 * Network Test.
 *
 * @author Eduardo Alfonso Sanchez
 * @since 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NetworkTest {

    @Test
    public void constructNetworkTest() {
        Network network = new Network(4);
        Assert.assertEquals("The number of nodes is wrong", network.getMatrixCopy().length, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructFailedNetworkTest() {
        Network network = new Network(-3);
    }

    @Test
    public void isConnectedTest() {
        Network network = new Network(8);
        network.connect(3, 7);

        Assert.assertTrue("The nodes must be connected", network.getMatrixCopy()[2][6]);
    }

    @Test
    public void isNotConnectTest() {
        Network network = new Network(8);
        network.connect(3, 7);

        Assert.assertFalse("The nodes must be not connected", network.getMatrixCopy()[1][3]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConnectionFailedSourceLessThanOneTest() {
        Network network = new Network(4);
        network.connect(0, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConnectionFailedTargetLessThanOneTest() {
        Network network = new Network(4);
        network.connect(1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConnectionFailedSourceGreaterThanTotalNodesTest() {
        Network network = new Network(4);
        network.connect(5, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConnectionFailedTargetGreaterThanTotalNodesTest() {
        Network network = new Network(6);
        network.connect(1, 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConnectionFailedEqualNodesTest() {
        Network network = new Network(6);
        network.connect(4, 4);
    }


    @Test
    public void queryExistConnectionTest() {
        Network network = new Network(8);
        network.connect(1, 3);
        network.connect(1, 6);
        network.connect(1, 8);
        network.connect(2, 8);
        network.connect(3, 7);
        network.connect(4, 5);
        network.connect(4, 6);
        network.connect(5, 6);
        network.connect(5, 7);
        network.connect(5, 8);


        Assert.assertTrue("There is not connection", network.query(2, 6));
    }

    @Test
    public void queryNotExistConnectionTest() {
        Network network = new Network(8);
        network.connect(1, 3);
        network.connect(1, 8);
        network.connect(2, 8);
        network.connect(3, 7);
        network.connect(4, 5);
        network.connect(4, 6);
        network.connect(5, 6);


        Assert.assertFalse("There is connection", network.query(2, 6));
    }

}

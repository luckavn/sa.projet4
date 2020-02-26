package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.integration.config.DataBaseConfigTest;
import java.sql.Connection;

public class DataBasePrepareServiceTest {

    DataBaseConfigTest dataBaseConfigTest = new DataBaseConfigTest();

    public void clearDataBaseEntries(){
        Connection connection = null;
        try{
            connection = dataBaseConfigTest.getConnection();

            //set parking entries to available
            connection.prepareStatement("update parking set available = true").execute();

            //clear ticket entries;
            connection.prepareStatement("truncate table ticket").execute();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseConfigTest.closeConnection(connection);
        }
    }


}
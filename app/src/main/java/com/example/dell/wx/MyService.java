package com.example.dell.wx;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }
      class MyBinder extends IMyAidlInterface.Stub{

          @Override
          public String getName() throws RemoteException {
              return "delamey";
          }
      }
}

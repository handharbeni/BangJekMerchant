package illiyin.mhandharbeni.servicemodule.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.TimeUnit;

import illiyin.mhandharbeni.servicemodule.service.intentservice.CategoryMenuService;
import illiyin.mhandharbeni.servicemodule.service.intentservice.CategoryMerchantService;
import illiyin.mhandharbeni.servicemodule.service.intentservice.MenuMerchantService;
import illiyin.mhandharbeni.servicemodule.service.intentservice.MerchantInformationService;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import rx.Scheduler;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


/**
 * Created by root on 17/07/17.
 */

public class MainService extends Service {
    private static int PERIODICALLY_CALL = 2 * 1000;
    private static int DELAY_CALL = 500;
    public static Boolean serviceRunning = false;
    private Session session;

    @Override
    public void onCreate() {
        session = new Session(getApplicationContext(), new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });
        syncCategoryMenu();
        syncCategoryMerchant();
        syncInfoMerchant();
        syncMenuMerchant();
    }
    public void syncCategoryMenu(){
        Action0 action0 = new Action0() {
            @Override
            public void call() {
                if (!checkIsRunning(CategoryMenuService.class)){
                    Intent is = new Intent(getBaseContext(), CategoryMenuService.class);
                    startService(is);
                }
            }
        };
        Scheduler.Worker worker = Schedulers.newThread().createWorker();
        worker.schedulePeriodically(action0, DELAY_CALL, PERIODICALLY_CALL, TimeUnit.MILLISECONDS);
    }
    private void syncCategoryMerchant(){
        Action0 action0 = new Action0() {
            @Override
            public void call() {
                if (!checkIsRunning(CategoryMerchantService.class)){
                    Intent is = new Intent(getBaseContext(), CategoryMerchantService.class);
                    startService(is);
                }
            }
        };
        Scheduler.Worker worker = Schedulers.newThread().createWorker();
        worker.schedulePeriodically(action0, DELAY_CALL, PERIODICALLY_CALL, TimeUnit.MILLISECONDS);
    }
    private void syncMenuMerchant(){
        Action0 action0 = new Action0() {
            @Override
            public void call() {
                if (!checkIsRunning(MenuMerchantService.class)){
                    if (!session.getToken().equalsIgnoreCase("nothing")){
                        Intent is = new Intent(getBaseContext(), MenuMerchantService.class);
                        startService(is);
                    }
                }
            }
        };
        Scheduler.Worker worker = Schedulers.newThread().createWorker();
        worker.schedulePeriodically(action0, DELAY_CALL, PERIODICALLY_CALL, TimeUnit.MILLISECONDS);
    }
    private void syncInfoMerchant(){
        Action0 action0 = new Action0() {
            @Override
            public void call() {
                if (!checkIsRunning(MerchantInformationService.class)){
                    if (!session.getToken().equalsIgnoreCase("nothing")){
                        Intent is = new Intent(getBaseContext(), MerchantInformationService.class);
                        startService(is);
                    }
                }
            }
        };
        Scheduler.Worker worker = Schedulers.newThread().createWorker();
        worker.schedulePeriodically(action0, DELAY_CALL, PERIODICALLY_CALL, TimeUnit.MILLISECONDS);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceRunning = true;
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        serviceRunning = false;
        super.onDestroy();
    }
    private boolean checkIsRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

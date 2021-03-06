package illiyin.mhandharbeni.servicemodule.service.intentservice;

import android.app.IntentService;
import android.content.Intent;

import illiyin.mhandharbeni.databasemodule.AdapterModel;

/**
 * Created by root on 11/24/17.
 */

public class MenuMerchantService extends IntentService {
    AdapterModel adapterModel;
    public MenuMerchantService() {
        super("Menu Merchant Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        adapterModel = new AdapterModel(getBaseContext());
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        adapterModel.syncMenuByRx();
    }
}

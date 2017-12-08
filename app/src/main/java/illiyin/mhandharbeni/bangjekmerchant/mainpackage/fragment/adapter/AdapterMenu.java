package illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by root on 12/8/17.
 */

public class AdapterMenu extends RealmBasedRecyclerViewAdapter<MenuMerchantModel, AdapterMenu.Holder> {
    private Context context;
    private MenuMerchantModel menuMerchantModel;
    private Crud crud;

    public AdapterMenu(Context context, RealmResults<MenuMerchantModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate, false);
        this.context = context;
        this.menuMerchantModel = new MenuMerchantModel();
        this.crud = new Crud(this.context, menuMerchantModel);
    }

    @Override
    public AdapterMenu.Holder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new AdapterMenu.Holder(inflater.inflate(R.layout.item_menu, viewGroup, false));
    }

    @Override
    public void onBindRealmViewHolder(AdapterMenu.Holder holder, int i) {
        menuMerchantModel = realmResults.get(i);
        holder.txtItemName.setText(menuMerchantModel.getMerchantMenu());
        holder.txtItemPrice.setText(menuMerchantModel.getPrice());
    }

    public class Holder extends RealmViewHolder {
        ImageView image;
        TextView txtItemName, txtItemPrice;
        public Holder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
        }
    }
}

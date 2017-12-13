package illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity.DetailMenu;
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
        holder.cardviewparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getContext().startActivity(new Intent(getContext(), DetailMenu.class));
            }
        });
    }

    public class Holder extends RealmViewHolder {
        ImageView image;
        TextView txtItemName, txtItemPrice;
        CardView cardviewparent;
        public Holder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            cardviewparent = itemView.findViewById(R.id.cardviewparent);
        }
    }
}

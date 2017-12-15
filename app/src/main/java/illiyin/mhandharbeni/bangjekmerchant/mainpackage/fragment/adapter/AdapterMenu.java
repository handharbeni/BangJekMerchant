package illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.listen.RvClick;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.utilslibrary.NumberFormat;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by root on 12/8/17.
 */

public class AdapterMenu extends RealmBasedRecyclerViewAdapter<MenuMerchantModel, AdapterMenu.Holder>{
    private Context context;
    private MenuMerchantModel menuMerchantModel;
    private Crud crud;
    private RvClick click;

    public AdapterMenu(Context context, RealmResults<MenuMerchantModel> realmResults, boolean automaticUpdate, RvClick click) {
        super(context, realmResults, automaticUpdate, false);
        this.context = context;
        this.menuMerchantModel = new MenuMerchantModel();
        this.crud = new Crud(this.context, menuMerchantModel);
        this.click = click;
    }

    @Override
    public AdapterMenu.Holder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new AdapterMenu.Holder(inflater.inflate(R.layout.item_menu, viewGroup, false));
    }

    @Override
    public void onBindRealmViewHolder(AdapterMenu.Holder holder, int i) {
        menuMerchantModel = realmResults.get(i);
        holder.txtItemName.setText(menuMerchantModel.getMerchantMenu());
        holder.txtItemPrice.setText(NumberFormat.format(Double.valueOf(menuMerchantModel.getPrice())));
        Glide.with(getContext()).load(menuMerchantModel.getPhoto()).into(holder.image);
        final String idMerchantMenu = menuMerchantModel.getIdMerchantMenu();
        holder.cardviewparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onClick(Integer.valueOf(idMerchantMenu));
            }
        });
    }

    public class Holder extends RealmViewHolder {
        ImageView image;
        TextView txtItemName,txtItemPrice;
        CardView cardviewparent;
        public Holder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.images);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            cardviewparent = itemView.findViewById(R.id.cardviewparent);
        }
    }
}
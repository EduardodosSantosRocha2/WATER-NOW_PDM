package app.example.waternow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AguaAdapter extends ArrayAdapter<AguaList> {
    private Context mContext;
    private List<AguaList> aguaList;

    public AguaAdapter(Context context, ArrayList<AguaList> list){
        super(context, 0, list);
        mContext = context;
        aguaList =  list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.agua_list,parent,false);
        AguaList currentAgua = aguaList.get(position);


        TextView Codigo = listItem.findViewById(R.id.textViewCodigo);
        Codigo.setText(currentAgua.getCod());

        TextView Marca = listItem.findViewById(R.id.textViewMarca);
        Marca.setText(currentAgua.getModelo());


        TextView Modelo = listItem.findViewById(R.id.textViewModelo);
        Modelo.setText(currentAgua.getMarca());


        return listItem;
    }

}

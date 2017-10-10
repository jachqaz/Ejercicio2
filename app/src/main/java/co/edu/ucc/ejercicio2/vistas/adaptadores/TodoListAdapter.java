package co.edu.ucc.ejercicio2.vistas.adaptadores;

import android.graphics.Paint;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.edu.ucc.ejercicio2.R;
import co.edu.ucc.ejercicio2.modelo.Tarea;

/**
 * Created by jach_ on 12/09/2017.
 */

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ItenTodoList> {
    private List<Tarea> dataset;
    private OnListenerItemCheck onListenerItemCheck;

    public TodoListAdapter(List<Tarea> dataset, OnListenerItemCheck onListenerItemCheck) {
        super();
        this.dataset = dataset;
        this.onListenerItemCheck = onListenerItemCheck;
    }

    @Override
    public ItenTodoList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ItenTodoList(view);
    }

    @Override
    public void onBindViewHolder(ItenTodoList holder, int position) {
        Tarea tarea = dataset.get(position);
        if(tarea.isRealizado()){
            holder.ItvTarea.setPaintFlags(holder.ItvTarea.getPaintFlags()
            | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.Itvfecha.setPaintFlags(holder.Itvfecha.getPaintFlags()
                    | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            holder.ItvTarea.setPaintFlags(holder.ItvTarea.getPaintFlags()
                    & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.Itvfecha.setPaintFlags(holder.Itvfecha.getPaintFlags()
                    & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.ItvTarea.setText(tarea.getNombre());
        holder.Itvfecha.setText(tarea.getFecha());
        holder.IchkTarea.setChecked(tarea.isRealizado());
    }
    public void setDataset(List<Tarea> dataset){
        this.dataset=dataset;
    }
    public void setItemDataset(Tarea tarea,  int index){
        this.dataset.set(index,tarea);
    }
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ItenTodoList extends RecyclerView.ViewHolder {

        @BindView(R.id.IchkTarea)
        AppCompatCheckBox IchkTarea;

        @BindView(R.id.ItvTarea)
        TextView ItvTarea;
        @BindView(R.id.Itvfecha)
        TextView Itvfecha;
        public ItenTodoList(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            IchkTarea= (AppCompatCheckBox) itemView.findViewById(R.id.IchkTarea);
//            ItvTarea= (TextView) itemView.findViewById(R.id.ItvTarea);
            if(onListenerItemCheck!=null){
                IchkTarea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onListenerItemCheck.itemCambioEstado(getAdapterPosition(),
                                IchkTarea.isChecked());
                    }
                });
            }
        }
    }

    public interface OnListenerItemCheck {
        void itemCambioEstado(int posicion, boolean realizada);
    }
}

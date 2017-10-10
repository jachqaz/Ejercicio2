package co.edu.ucc.ejercicio2.vistas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.edu.ucc.ejercicio2.LoginActivity;
import co.edu.ucc.ejercicio2.R;
import co.edu.ucc.ejercicio2.entidades.Ntareas;
import co.edu.ucc.ejercicio2.modelo.Tarea;
import co.edu.ucc.ejercicio2.vistas.adaptadores.TodoListAdapter;
import co.edu.ucc.ejercicio2.vistas.presenters.IListPresenter;
import co.edu.ucc.ejercicio2.vistas.presenters.ListPresenter;

public class PrincipalActivity extends AppCompatActivity implements
        IListView, TodoListAdapter.OnListenerItemCheck {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    //List
    private IListPresenter listPresenter;
    private TodoListAdapter adapter;
    int dayOfMonth = 0, month = 0, year = 0, id = 0;
    String email = "";
    //Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    @BindView(R.id.recicler)
    RecyclerView recicler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        email = getIntent().getStringExtra("email");
        email = email.replace(".", ",");
        //Toast.makeText(PrincipalActivity.this, email, Toast.LENGTH_SHORT).show();
        //List
        listPresenter = new ListPresenter(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recicler.setLayoutManager(llm);
        List<Tarea> lsTarea = listPresenter.obtenerTareas();
        adapter=new TodoListAdapter(lsTarea,this);
        recicler.setAdapter(adapter);
        //firebase(email);

    }

//   public void firebase(final String email) {
//        myRef = database.getReference("Ntareas");
//        myRef.child(email).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Ntareas value = dataSnapshot.getValue(Ntareas.class);
//                id = value.getValor();
//                if (id > 0) {
//                    for (int Contador = 1; Contador <= id; Contador++) {
//                        myRef = database.getReference("usuarios");
//                        myRef.child(email).child(String.valueOf(Contador)).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                Tarea value = dataSnapshot.getValue(Tarea.class);
//                                listPresenter.addTarea(value.getNombre(), value.getFecha());
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    @OnClick(R.id.PAdd)
    public void Modal() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PrincipalActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.modal, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        Button mLogin = (Button) mView.findViewById(R.id.MGuardar);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAddTarea(mView);
                dialog.dismiss();
            }
        });
        Button mbfecha = (Button) mView.findViewById(R.id.MBfecha);
        mbfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fecha(mView);
            }
        });

    }


    @OnClick(R.id.login_button)
    public void login() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void Fecha(View v) {
        final EditText Fecha = (EditText) v.findViewById(R.id.MFecha);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            final Calendar c = Calendar.getInstance();
            dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Fecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }
                    , dayOfMonth, month, year);
            datePickerDialog.updateDate(year, month, dayOfMonth);
            datePickerDialog.show();
        }
    }

    //List
    @Override
    public void clickAddTarea(View mView) {
        id++;
        EditText aux = (EditText) mView.findViewById(R.id.Mtarea);
        String declTarea = aux.getText().toString();
        aux = (EditText) mView.findViewById(R.id.MFecha);
        String Fecha = aux.getText().toString();
        listPresenter.addTarea(declTarea, Fecha);
        //Firebase

        Tarea objTarea = new Tarea();
        objTarea.setFecha(Fecha);
        objTarea.setNombre(declTarea);
        objTarea.setRealizado(false);
        myRef = database.getReference("usuarios");
        myRef.child(email).child(String.valueOf(id)).setValue(objTarea);
        Ntareas numero = new Ntareas();
        numero.setValor(id);
        myRef = database.getReference("Ntareas");
        myRef.child(email).setValue(numero);
    }


    @Override
    public void refrescarListaTareas(List<Tarea> lstTarea) {
        adapter.setDataset(lstTarea);
        recicler.getAdapter().notifyDataSetChanged();
        recicler.scrollToPosition(recicler.getAdapter().getItemCount() - 1);
        //Cescribe_tarea.setText("");
    }

    @Override
    public void refrescarTarea(Tarea tarea, int posicion) {
        adapter.setItemDataset(tarea,posicion);
        recicler.getAdapter().notifyItemChanged(posicion);
    }


    @Override
    public void itemCambioEstado(int posicion, boolean realizada) {
        listPresenter.itemCambioEstado(posicion, realizada);
    }
}

package org.sysmob.biblivirti.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.sysmob.biblivirti.R;
import org.sysmob.biblivirti.comparators.UsuarioComparatorByUsnid;
import org.sysmob.biblivirti.enums.ETipoGrupo;
import org.sysmob.biblivirti.model.Grupo;
import org.sysmob.biblivirti.model.Usuario;

import java.util.Collections;
import java.util.List;

/**
 * Created by djalmocruzjr on 30/01/2017.
 */

public class PesquisarGruposFragmentAdapter extends RecyclerView.Adapter<PesquisarGruposFragmentAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<Grupo> grupos;
    private Usuario loggedUser;

    public PesquisarGruposFragmentAdapter(Context context, List<Grupo> grupos, Usuario loggedUser) {
        this.context = context;
        this.grupos = grupos;
        this.loggedUser = loggedUser;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_pesquisar_grupos, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return this.grupos != null ? this.grupos.size() : 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Grupo grupo = this.grupos.get(position);
        holder.imageGrupoPrivado.setVisibility(grupo.getGrctipo().equals(ETipoGrupo.FECHADO) ? View.VISIBLE : View.INVISIBLE);
        holder.imageAdmin.setImageBitmap(BitmapFactory.decodeResource(this.context.getResources(), grupo.getAdmin().getUsnid() == this.loggedUser.getUsnid() ? R.mipmap.ic_king_100px_blue : R.mipmap.ic_king_100px_gray));
        holder.imageGRCFOTO.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_app_group_80px));
        if (grupo.getGrcfoto() != null && !grupo.getGrcfoto().equals("null")) {
            Picasso.with(this.context).load(grupo.getGrcfoto()).into(holder.imageGRCFOTO);
        }
        holder.textGRCNOME.setText(grupo.getGrcnome().toString());
        holder.textAICDESC.setText(grupo.getAreaInteresse().getAicdesc().toString());
        holder.textUSCLOGN.setText(grupo.getAdmin().getUsnid() == this.loggedUser.getUsnid() ? "Você" : grupo.getAdmin().getUsclogn().toString());
        // Verifica se o usuario logado EH um membro do grupo
        if (Collections.binarySearch(grupo.getUsuarios(), this.loggedUser, new UsuarioComparatorByUsnid()) >= 0) {
            holder.buttonSairParticiparGrupo.setText(context.getString(R.string.adapter_pesquisar_grupos_fragment_button_sair_text));
            holder.buttonSairParticiparGrupo.setBackgroundColor(context.getResources().getColor(R.color.colorRedDark));
            holder.buttonSairParticiparGrupo.setVisibility(this.loggedUser.getUsnid() != grupo.getAdmin().getUsnid() ? View.VISIBLE : View.GONE);
        } else {
            holder.buttonSairParticiparGrupo.setText(context.getString(R.string.adapter_pesquisar_grupos_fragment_button_participar_text));
            holder.buttonSairParticiparGrupo.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.buttonSairParticiparGrupo.setVisibility(grupo.getGrctipo() == ETipoGrupo.ABERTO ? View.VISIBLE : View.GONE);
        }
        holder.buttonSairParticiparGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onCLick(view, position);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ProgressBar progressBar;
        ImageView imageGrupoPrivado;
        ImageView imageAdmin;
        ImageView imageGRCFOTO;
        TextView textGRCNOME;
        TextView textAICDESC;
        TextView textUSCLOGN;
        Button buttonSairParticiparGrupo;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            imageGrupoPrivado = (ImageView) view.findViewById(R.id.imageGrupoPrivado);
            imageAdmin = (ImageView) view.findViewById(R.id.imageAdmin);
            imageGRCFOTO = (ImageView) view.findViewById(R.id.imageGRCFOTO);
            textGRCNOME = (TextView) view.findViewById(R.id.textGRCNOME);
            textAICDESC = (TextView) view.findViewById(R.id.textAICDESC);
            textUSCLOGN = (TextView) view.findViewById(R.id.textUSCLOGN);
            buttonSairParticiparGrupo = (Button) view.findViewById(R.id.buttonSairParticiparGrupo);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onCLick(view, getAdapterPosition());
            }
        }

    }

    public interface OnItemClickListener {
        public void onCLick(View view, int position);
    }

}

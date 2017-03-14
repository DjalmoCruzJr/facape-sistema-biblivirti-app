package org.sysmob.biblivirti.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sysmob.biblivirti.enums.EStatusGrupo;
import org.sysmob.biblivirti.enums.ETipoGrupo;
import org.sysmob.biblivirti.enums.EUsuarioStatus;
import org.sysmob.biblivirti.model.AreaInteresse;
import org.sysmob.biblivirti.model.Grupo;
import org.sysmob.biblivirti.model.Usuario;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public abstract class BiblivirtiParser {

    public static Usuario parseToUsuario(JSONObject json) throws JSONException {
        Usuario usuario = null;
        if (json != null) {
            usuario = new Usuario();
            usuario.setUsnid(json.getInt(Usuario.FIELD_USNID));
            usuario.setUscfbid(json.getString(Usuario.FIELD_USCFBID));
            usuario.setUscnome(json.getString(Usuario.FIELD_USCNOME));
            usuario.setUscmail(json.getString(Usuario.FIELD_USCMAIL));
            usuario.setUsclogn(json.getString(Usuario.FIELD_USCLOGN));
            usuario.setUscfoto(json.getString(Usuario.FIELD_USCFOTO));
            usuario.setUscsenh(json.opt(Usuario.FIELD_USCSENH) == null ? null : json.getString(Usuario.FIELD_USCSENH));
            usuario.setUscstat(EUsuarioStatus.ATIVO.getValue() == json.getString(Usuario.FIELD_USCSTAT).charAt(0) ? EUsuarioStatus.ATIVO : EUsuarioStatus.INATIVO);
            usuario.setUsdcadt(Timestamp.valueOf(json.getString(Usuario.FIELD_USDCADT)));
            usuario.setUsdaldt(Timestamp.valueOf(json.getString(Usuario.FIELD_USDALDT)));
        }
        return usuario;
    }

    public static List<Usuario> parseToUsuarios(JSONArray json) throws JSONException {
        List<Usuario> usuarios = null;
        if (json != null) {
            usuarios = new ArrayList<>();
            for (int i = 0; i < json.length(); i++) {
                Usuario usuario = null;
                usuario = parseToUsuario(json.getJSONObject(i));
                usuarios.add(usuario);
            }
        }
        if (usuarios != null && usuarios.size() > 0)
            return usuarios;
        return null;
    }

    public static AreaInteresse parseToAreaInteresse(JSONObject json) throws JSONException {
        AreaInteresse areaInteresse = null;
        if (json != null) {
            areaInteresse = new AreaInteresse();
            areaInteresse.setAinid(json.getInt(AreaInteresse.FIELD_AINID));
            areaInteresse.setAicdesc(json.getString(AreaInteresse.FIELD_AICDESC));
            areaInteresse.setAidcadt(Timestamp.valueOf(json.getString(AreaInteresse.FIELD_AIDCADT)));
            areaInteresse.setAidaldt(Timestamp.valueOf(json.getString(AreaInteresse.FIELD_AIDALDT)));
        }
        return areaInteresse;

    }

    public static List<AreaInteresse> parseToAreasinteresse(JSONArray json) throws JSONException {
        List<AreaInteresse> areasInteresse = null;
        if (json != null) {
            areasInteresse = new ArrayList<>();
            for (int i = 0; i < json.length(); i++) {
                AreaInteresse areaInteresse = null;
                areaInteresse = parseToAreaInteresse(json.getJSONObject(i));
                areasInteresse.add(areaInteresse);
            }
        }
        if (areasInteresse != null && areasInteresse.size() > 0)
            return areasInteresse;
        return null;
    }

    public static Grupo parseToGrupo(JSONObject json) throws JSONException {
        Grupo grupo = null;
        if (json != null) {
            grupo = new Grupo();
            grupo.setGrnid(json.getInt(Grupo.FIELD_GRNID));
            grupo.setAreaInteresse(parseToAreaInteresse(json.getJSONObject(Grupo.FIELD_GRAREAOFINTEREST)));
            grupo.setGrcnome(json.getString(Grupo.FIELD_GRCNOME));
            grupo.setGrcfoto(json.getString(Grupo.FIELD_GRCFOTO));
            grupo.setGrctipo(ETipoGrupo.ABERTO.getValue() == json.getString(Grupo.FIELD_GRCTIPO).charAt(0) ? ETipoGrupo.ABERTO : ETipoGrupo.FECHADO);
            grupo.setGrcstat(EStatusGrupo.ATIVO.getValue() == json.getString(Grupo.FIELD_GRCSTAT).charAt(0) ? EStatusGrupo.ATIVO : EStatusGrupo.INATIVO);
            grupo.setGrdcadt(Timestamp.valueOf(json.getString(Grupo.FIELD_GRDCADT)));
            grupo.setGrdaldt(Timestamp.valueOf(json.getString(Grupo.FIELD_GRDALDT)));
            grupo.setAdmin(parseToUsuario(json.getJSONObject(Grupo.FIELD_GRADMIN)));
            grupo.setUsuarios(json.opt(Grupo.FIELD_GRUSERS) != null ? parseToUsuarios(json.getJSONArray(Grupo.FIELD_GRUSERS)) : null);
        }
        return grupo;
    }

    public static List<Grupo> parseToGrupos(JSONArray json) throws JSONException {
        List<Grupo> grupos = null;
        if (json != null) {
            grupos = new ArrayList<>();
            for (int i = 0; i < json.length(); i++) {
                Grupo grupo = null;
                grupo = parseToGrupo(json.getJSONObject(i));
                grupos.add(grupo);
            }
        }
        if (grupos != null && grupos.size() > 0)
            return grupos;
        return null;
    }
}

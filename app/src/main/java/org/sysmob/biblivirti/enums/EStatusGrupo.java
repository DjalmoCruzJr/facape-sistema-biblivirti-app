package org.sysmob.biblivirti.enums;

/**
 * Created by djalmocruzjr on 30/01/2017.
 */
public enum EStatusGrupo {
    ATIVO('A'),
    INATIVO('I');

    private char value;

    EStatusGrupo() {
    }

    EStatusGrupo(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }
}

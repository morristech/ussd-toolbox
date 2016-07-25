package co.sigmoidlabs.bankussdtoolbox.model.data;

/**
 * Created by Efe on 25/07/2016.
 */
public class Field<T extends Field.Type> {


    public enum Type {

        Boolean,
        Number,
        Enum;

    }

    public static class Value {

        private Field field;

        Value(Field field) {

            this.field = field;
        }
    }
}

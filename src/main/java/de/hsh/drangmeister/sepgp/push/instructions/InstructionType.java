package de.hsh.drangmeister.sepgp.push.instructions;

/**
 * @author robin
 */
public enum InstructionType {

    BOOLEAN(0x1), CHAR(0x2), STRING(0x4), EXEC(0x8), FLOAT(0x10), INT(0x20), PRINT(0x40),
    ARRAY_STRING(0x80), ARRAY_INT(0x100), ARRAY_FLOAT(0x200);

    private int value;

    private InstructionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

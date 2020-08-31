package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.IExecutable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author robin
 */
public class InstructionList {

    private static final Map<Integer, List<IExecutable>> map;

    static {
        map = new HashMap<>();

        map.put(getKey(InstructionType.BOOLEAN), List.of(new BooleanInstructions.Empty(), new BooleanInstructions.Swap(), new BooleanInstructions.Equal(), new BooleanInstructions.InvertFirstThenAnd(),
                new BooleanInstructions.Flush(), new BooleanInstructions.Rot(), new BooleanInstructions.And(), new BooleanInstructions.InvertSecondThenAnd(), new BooleanInstructions.Xor(),
                new BooleanInstructions.Not(), new BooleanInstructions.Or(), new BooleanInstructions.Dup(), new BooleanInstructions.Pop()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.CHAR), List.of(new CharInstructions.IsWhitespace(), new CharInstructions.Empty(), new CharInstructions.IsLetter(), new CharInstructions.Equal(), new CharInstructions.IsDigit()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.CHAR, InstructionType.STRING), List.of(new StringInstructions.ContainsChar()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.EXEC), List.of(new ExecInstructions.Equal(), new ExecInstructions.If(), new ExecInstructions.When(),
                new ExecInstructions.DoWhile(), new ExecInstructions.While(), new ExecInstructions.Empty()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.FLOAT), List.of(new BooleanInstructions.FromFloat(), new FloatInstructions.Empty(),
                new FloatInstructions.LessThan(), new FloatInstructions.GreaterThan(), new FloatInstructions.FromBoolean(), new FloatInstructions.Equal(), new FloatInstructions.LessThanOrEquals(), new FloatInstructions.GreaterThanOrEquals()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.FLOAT, InstructionType.ARRAY_FLOAT), List.of(new FloatArrayInstructions.Contains()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.INT), List.of(new IntInstructions.Equal(), new BooleanInstructions.Yank(), new IntInstructions.GreaterThanOrEquals(),
                new IntInstructions.GreaterThan(), new IntInstructions.LessThan(), new IntInstructions.LessThanOrEquals(), new BooleanInstructions.Shove(), new IntInstructions.Empty(), new IntInstructions.FromBoolean(),
                new BooleanInstructions.FromInteger(), new BooleanInstructions.StackDepth(), new BooleanInstructions.YankDup()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.INT, InstructionType.ARRAY_INT), List.of(new IntArrayInstructions.Contains()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.STRING), List.of(new StringInstructions.Equal(), new StringInstructions.EmptyString(), new StringInstructions.FromBoolean(), new StringInstructions.Contains(),
                new StringInstructions.Empty()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.STRING, InstructionType.ARRAY_STRING), List.of(new StringArrayInstructions.Contains()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.ARRAY_FLOAT), List.of(new FloatArrayInstructions.EmptyArray(), new FloatArrayInstructions.Empty(), new FloatArrayInstructions.Equal()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.ARRAY_INT), List.of(new IntArrayInstructions.EmptyArray(), new IntArrayInstructions.Empty(), new IntArrayInstructions.Equal()));

        map.put(getKey(InstructionType.BOOLEAN, InstructionType.ARRAY_STRING), List.of(new StringArrayInstructions.EmptyArray(), new StringArrayInstructions.Empty(), new StringArrayInstructions.Equal()));

        map.put(getKey(InstructionType.CHAR), List.of(new CharInstructions.Dup(), new CharInstructions.Swap(), new CharInstructions.Flush(), new CharInstructions.Rot(), new CharInstructions.Pop()));

        map.put(getKey(InstructionType.CHAR, InstructionType.EXEC, InstructionType.STRING), List.of(new ExecInstructions.StringIterate()));

        map.put(getKey(InstructionType.CHAR, InstructionType.FLOAT), List.of(new CharInstructions.FromFloat(), new FloatInstructions.FromChar()));

        map.put(getKey(InstructionType.CHAR, InstructionType.INT), List.of(new CharInstructions.Shove(), new CharInstructions.StackDepth(), new IntInstructions.FromChar(), new CharInstructions.Yank(),
                new CharInstructions.YankDup(), new CharInstructions.FromInt()));

        map.put(getKey(InstructionType.CHAR, InstructionType.INT, InstructionType.STRING), List.of(new StringInstructions.OccurrencesOfChar(), new StringInstructions.SetNthChar(),
                new StringInstructions.NthChar(), new StringInstructions.IndexOfChar()));

        map.put(getKey(InstructionType.CHAR, InstructionType.STRING), List.of(new StringInstructions.RemoveChar(), new CharInstructions.AllFromString(), new StringInstructions.ReplaceChar(),
                new StringInstructions.ReplaceFirstChar(), new StringInstructions.Concat(), new StringInstructions.FromChar(), new StringInstructions.First(), new StringInstructions.Last()));

        map.put(getKey(InstructionType.EXEC), List.of(new ExecInstructions.Y(), new ExecInstructions.Pop(), new ExecInstructions.Rot(), new ExecInstructions.S(), new ExecInstructions.K(),
                new ExecInstructions.Flush(), new ExecInstructions.Swap(), new ExecInstructions.Dup(), new ExecInstructions.NoOp(), new ExecInstructions.NoOpOpenParen(), new ExecInstructions.NoOpDeletePrevParenPair()));

        map.put(getKey(InstructionType.EXEC, InstructionType.FLOAT, InstructionType.ARRAY_FLOAT), List.of(new FloatArrayInstructions.Iterate()));

        map.put(getKey(InstructionType.EXEC, InstructionType.INT, InstructionType.ARRAY_INT), List.of(new IntArrayInstructions.Iterate()));

        map.put(getKey(InstructionType.EXEC, InstructionType.STRING, InstructionType.ARRAY_STRING), List.of(new StringArrayInstructions.Iterate()));

        map.put(getKey(InstructionType.EXEC, InstructionType.INT), List.of(new ExecInstructions.StackDepth(), new ExecInstructions.DoTimes(),
                new ExecInstructions.DoCount(), new ExecInstructions.DoRange(), new ExecInstructions.Yank(), new ExecInstructions.YankDup(), new ExecInstructions.Shove()));

        map.put(getKey(InstructionType.FLOAT), List.of(new FloatInstructions.Rot(), new FloatInstructions.Sin(), new FloatInstructions.Cos(), new FloatInstructions.Swap(), new FloatInstructions.Div(), new FloatInstructions.Inc(),
                new FloatInstructions.Minus(), new FloatInstructions.Flush(), new FloatInstructions.Plus(), new FloatInstructions.Tan(), new FloatInstructions.Mult(), new FloatInstructions.Max(), new FloatInstructions.Pop(),
                new FloatInstructions.Min(), new FloatInstructions.Dup(), new FloatInstructions.Dec(), new FloatInstructions.Mod()));

        map.put(getKey(InstructionType.FLOAT, InstructionType.INT), List.of(new FloatInstructions.Yank(), new FloatInstructions.FromInteger(), new FloatInstructions.StackDepth(), new FloatInstructions.Shove(),
                new FloatInstructions.YankDup(), new IntInstructions.FromFloat()));

        map.put(getKey(InstructionType.FLOAT, InstructionType.INT, InstructionType.ARRAY_FLOAT), List.of(new FloatArrayInstructions.IndexOf(), new FloatArrayInstructions.OccurrencesOf(),
                new FloatArrayInstructions.Nth(), new FloatArrayInstructions.Set()));

        map.put(getKey(InstructionType.FLOAT, InstructionType.STRING), List.of(new FloatInstructions.FromString(), new StringInstructions.FromFloat()));

        map.put(getKey(InstructionType.FLOAT, InstructionType.ARRAY_FLOAT), List.of(new FloatArrayInstructions.Add(), new FloatArrayInstructions.Remove(), new FloatArrayInstructions.Last(), new FloatArrayInstructions.First(),
                new FloatArrayInstructions.ReplaceFirst(), new FloatArrayInstructions.Replace(), new FloatArrayInstructions.PushAll()));

        map.put(getKey(InstructionType.INT), List.of(new IntInstructions.Plus(), new IntInstructions.Swap(), new IntInstructions.Yank(), new IntInstructions.Dup(), new IntInstructions.YankDup(), new IntInstructions.Flush(),
                new IntInstructions.Shove(), new IntInstructions.Mult(), new IntInstructions.StackDepth(), new IntInstructions.Div(), new IntInstructions.Inc(), new IntInstructions.Max(), new IntInstructions.Minus(), new IntInstructions.Mod(),
                new IntInstructions.Rot(), new IntInstructions.Dec(), new IntInstructions.Min(), new IntInstructions.Pop()));

        map.put(getKey(InstructionType.INT, InstructionType.STRING), List.of(new StringInstructions.Substring(), new StringInstructions.Take(), new StringInstructions.FromInt(), new StringInstructions.StackDepth(),
                new IntInstructions.FromString(), new StringInstructions.Yank(), new StringInstructions.YankDup(), new StringInstructions.Length(), new StringInstructions.Shove()));

        map.put(getKey(InstructionType.INT, InstructionType.STRING, InstructionType.ARRAY_STRING), List.of(new StringArrayInstructions.IndexOf(), new StringArrayInstructions.Set(), new StringArrayInstructions.Nth(),
                new StringArrayInstructions.OccurrencesOf()));

        map.put(getKey(InstructionType.INT, InstructionType.ARRAY_FLOAT), List.of(new FloatArrayInstructions.Shove(), new FloatArrayInstructions.Length(), new FloatArrayInstructions.StackDepth(), new FloatArrayInstructions.SubArray(),
                new FloatArrayInstructions.Yank(), new FloatArrayInstructions.Take(), new FloatArrayInstructions.YankDup()));

        map.put(getKey(InstructionType.INT, InstructionType.ARRAY_INT), List.of(new IntArrayInstructions.Remove(), new IntArrayInstructions.PushAll(), new IntArrayInstructions.Yank(), new IntArrayInstructions.SubArray(),
                new IntArrayInstructions.Last(), new IntArrayInstructions.First(), new IntArrayInstructions.Shove(), new IntArrayInstructions.IndexOf(), new IntArrayInstructions.OccurrencesOf(), new IntArrayInstructions.Replace(),
                new IntArrayInstructions.ReplaceFirst(), new IntArrayInstructions.Take(), new IntArrayInstructions.StackDepth(), new IntArrayInstructions.Nth(), new IntArrayInstructions.Set(), new IntArrayInstructions.Length(),
                new IntArrayInstructions.YankDup(), new IntArrayInstructions.Add()));

        map.put(getKey(InstructionType.INT, InstructionType.ARRAY_STRING), List.of(new StringArrayInstructions.StackDepth(), new StringArrayInstructions.SubArray(), new StringArrayInstructions.Take(), new StringArrayInstructions.Shove(),
                new StringArrayInstructions.Yank(), new StringArrayInstructions.Length(), new StringArrayInstructions.YankDup()));

        map.put(getKey(InstructionType.PRINT), List.of(new PrintInstructions.PrintNewLine()));

        map.put(getKey(InstructionType.PRINT, InstructionType.BOOLEAN), List.of(new PrintInstructions.PrintBoolean()));

        map.put(getKey(InstructionType.PRINT, InstructionType.CHAR), List.of(new PrintInstructions.PrintChar()));

        map.put(getKey(InstructionType.PRINT, InstructionType.FLOAT), List.of(new PrintInstructions.PrintFloat()));

        map.put(getKey(InstructionType.PRINT, InstructionType.INT), List.of(new PrintInstructions.PrintInt()));

        map.put(getKey(InstructionType.PRINT, InstructionType.STRING), List.of(new PrintInstructions.PrintString()));

        map.put(getKey(InstructionType.PRINT, InstructionType.ARRAY_FLOAT), List.of(new PrintInstructions.PrintFloatArray()));

        map.put(getKey(InstructionType.PRINT, InstructionType.ARRAY_INT), List.of(new PrintInstructions.PrintIntArray()));

        map.put(getKey(InstructionType.PRINT, InstructionType.ARRAY_STRING), List.of(new PrintInstructions.PrintStringArray()));

        map.put(getKey(InstructionType.STRING), List.of(new StringInstructions.Pop(), new StringInstructions.Rot(), new StringInstructions.Rest(), new StringInstructions.Reverse(), new StringInstructions.Swap(), new StringInstructions.SplitOnSpace(),
                new StringInstructions.Flush(), new StringInstructions.ReplaceFirst(), new StringInstructions.ButLast(), new StringInstructions.Concat(), new StringInstructions.Replace(), new StringInstructions.Dup(), new StringInstructions.ParseToChars()));

        map.put(getKey(InstructionType.STRING, InstructionType.ARRAY_STRING), List.of(new StringArrayInstructions.Remove(), new StringArrayInstructions.Add(), new StringArrayInstructions.First(),
                new StringArrayInstructions.PushAll(), new StringArrayInstructions.Last(), new StringArrayInstructions.ReplaceFirst(), new StringArrayInstructions.Replace()));

        map.put(getKey(InstructionType.ARRAY_FLOAT), List.of(new FloatArrayInstructions.Dup(), new FloatArrayInstructions.Pop(), new FloatArrayInstructions.Rot(), new FloatArrayInstructions.Swap(),
                new FloatArrayInstructions.Flush(), new FloatArrayInstructions.Reverse(), new FloatArrayInstructions.Rest(), new FloatArrayInstructions.Concat(), new FloatArrayInstructions.ButLast()));

        map.put(getKey(InstructionType.ARRAY_INT), List.of(new IntArrayInstructions.Swap(), new IntArrayInstructions.ButLast(), new IntArrayInstructions.Flush(), new IntArrayInstructions.Rest(), new IntArrayInstructions.Concat(),
                new IntArrayInstructions.Rot(), new IntArrayInstructions.Reverse(), new IntArrayInstructions.Pop(), new IntArrayInstructions.Dup()));

        map.put(getKey(InstructionType.ARRAY_STRING), List.of(new StringArrayInstructions.Dup(), new StringArrayInstructions.Rot(), new StringArrayInstructions.Rest(), new StringArrayInstructions.Reverse(), new StringArrayInstructions.ButLast(),
                new StringArrayInstructions.Concat(), new StringArrayInstructions.Pop(), new StringArrayInstructions.Flush(), new StringArrayInstructions.Swap()));
    }

    public static List<IExecutable> getInstructions(List<InstructionType> types) {
        List<IExecutable> list = new ArrayList<>();
        int key = getKey(types);
        for (Map.Entry<Integer, List<IExecutable>> ent : map.entrySet()) {

            if ((ent.getKey() & key) == ent.getKey()) {
                list.addAll(ent.getValue());
            }
        }

        return list;
    }

    private static int getKey(List<InstructionType> types) {
        int key = 0;
        for (InstructionType type : types) {
            key |= type.getValue();
        }
        return key;
    }

    private static int getKey(InstructionType... types) {
       return getKey(List.of(types));
    }

}

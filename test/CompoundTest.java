import me.tom.sparse.bukkit.nbt.Compound;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CompoundTest
{
	public static void main(String[] args) throws IOException
	{
		Compound compound = new Compound();
		System.out.println(compound);
		compound.set(
				"a", 1,
				"b", 2,
				"c", 3,
				"lol", Arrays.asList("abc", "def", "ghi", "jkl", "mno", "pqr", "stu", "vwx", "yz"),
				"bytes", new byte[] {99, 98, 91, 82, 73, 64, 55, 46, 37, 28, 19, 0},
				"nested", new Compound(
						"first", "☺☻♥♦♣♠•◘○",
						"second", (short)5,
						"third", Long.MAX_VALUE,
						"repeated", "But it wasn't repeated.",
						"repeated", "I just repeated it."
				)
		);
		System.out.println(compound);
		
		compound.write(new File("./test.nbt"));
		
		System.out.println();
		
		compound = Compound.read(new File("./test.nbt"));
		System.out.println(compound);
	}
}

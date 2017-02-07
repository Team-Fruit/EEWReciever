package net.teamfruit.eewreciever2;

public class Debug {

	public static void main(final String[] args) throws Exception {
		final long start = System.currentTimeMillis();
		for (long i = 1L; i<4294967296L; i++)
			Math.log(Math.PI);
		System.out.println(System.currentTimeMillis()-start);
	}
}

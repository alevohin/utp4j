package ch.uzh.csg.utp4j.data;

import static ch.uzh.csg.utp4j.data.bytes.UnsignedTypesUtil.longToUbyte;

import java.util.Arrays;

public class SelectiveAckHeaderExtension extends UtpHeaderExtension {

	private byte nextExtension;
	private byte[] bitMask;
	
	/* Bit mappings */
	public static byte[] BITMAP = { 1, 2, 4, 8, 16, 32, 64, (byte) 128};
	
	public static boolean isBitMarked(byte bitmask, int number) {
		if (number < 2 || number > 9 ) {
			return false;
		} else {
			boolean returnvalue = (BITMAP[number - 2] & bitmask) == BITMAP[number - 2];
			return returnvalue;
		}
	}
	
	@Override
	public byte getNextExtension() {
		return nextExtension;
	}
	@Override
	public void setNextExtension(byte nextExtension) {
		this.nextExtension = nextExtension;
	}
	@Override
	public byte getLength() {
		return longToUbyte(bitMask.length);
	}

	@Override
	public byte[] getBitMask() {
		return bitMask;
	}
	@Override
	public void setBitMask(byte[] bitMask) {
		this.bitMask = bitMask;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} 
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SelectiveAckHeaderExtension)) {
			return false;
		} 
		SelectiveAckHeaderExtension s = (SelectiveAckHeaderExtension) obj;
		return Arrays.equals(toByteArray(), s.toByteArray());
	}
	
	@Override
	public byte[] toByteArray() {
		//TODO: not create a new byte array
		byte[] array = new byte[2 + bitMask.length];
		array[0] = nextExtension;
		array[1] = longToUbyte(bitMask.length);
		for (int i = 0; i < bitMask.length; i++) {
			array[i + 2] = bitMask[i];
		}
		return array;
	}
}

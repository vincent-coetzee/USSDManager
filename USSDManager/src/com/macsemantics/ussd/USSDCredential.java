package com.macsemantics.ussd;

import java.nio.ByteBuffer;

public class USSDCredential
	{
	public String personName;
	public String hash;
	public String userName;
	public String password;
	public long timeStamp;
	public static final int BufferSize = 32 + 16 + 64;
	private static final String PADDING_STRING = "000000000000000000000000000000000000000000000000000000000000";
	private ByteBuffer bytes;
	
	public void calculateHash()
		{
		byte[] bufferBytes;
		long hashCode;
		
		bytes = ByteBuffer.allocate(BufferSize);
		putString(padStringToLength(userName,32));
		putString(padStringToLength(password,16));
		bytes.putLong(timeStamp);
		bufferBytes = bytes.array();
		hashCode = hash64(bufferBytes,timeStamp);
		hash = Long.toHexString(hashCode);
		}
	
	public static String timeToken()
		{
		return(Long.toHexString(System.currentTimeMillis()));
		}
	
	protected void putString(String aString)
		{
		int index;
		
		for (index=0;index<aString.length();index++)
			{
			bytes.putChar(aString.charAt(index));
			}
		}
	
	public static String padStringToLength(String string,int length)
		{
		int additionalLength;
		
		additionalLength = length - string.length();
		return(string + PADDING_STRING.substring(0,additionalLength));
		}
	
	public static long hash64(final byte[] k, final long initval) 
		{
        /* Set up the internal state */
        long a = initval;
        long b = initval;
        /* the golden ratio; an arbitrary value */
        long c = 0x9e3779b97f4a7c13L;
        int len = k.length;

        /*---------------------------------------- handle most of the key */
        int i = 0;
        while(len >= 24) {
            a += gatherLongLE(k, i);
            b += gatherLongLE(k, i + 8);
            c += gatherLongLE(k, i + 16);

            /* mix64(a, b, c); */
            a -= b;
            a -= c;
            a ^= (c >> 43);
            b -= c;
            b -= a;
            b ^= (a << 9);
            c -= a;
            c -= b;
            c ^= (b >> 8);
            a -= b;
            a -= c;
            a ^= (c >> 38);
            b -= c;
            b -= a;
            b ^= (a << 23);
            c -= a;
            c -= b;
            c ^= (b >> 5);
            a -= b;
            a -= c;
            a ^= (c >> 35);
            b -= c;
            b -= a;
            b ^= (a << 49);
            c -= a;
            c -= b;
            c ^= (b >> 11);
            a -= b;
            a -= c;
            a ^= (c >> 12);
            b -= c;
            b -= a;
            b ^= (a << 18);
            c -= a;
            c -= b;
            c ^= (b >> 22);
            /* mix64(a, b, c); */

            i += 24;
            len -= 24;
        }

        /*------------------------------------- handle the last 23 bytes */
        c += k.length;

        if(len > 0) {
            if(len >= 8) {
                a += gatherLongLE(k, i);
                if(len >= 16) {
                    b += gatherLongLE(k, i + 8);
                    // this is bit asymmetric; LSB is reserved for length (see
                    // above)
                    if(len > 16) {
                        c += (gatherPartialLongLE(k, i + 16, len - 16) << 8);
                    }
                } else if(len > 8) {
                    b += gatherPartialLongLE(k, i + 8, len - 8);
                }
            } else {
                a += gatherPartialLongLE(k, i, len);
            }
        }

        /* mix64(a, b, c); */
        a -= b;
        a -= c;
        a ^= (c >> 43);
        b -= c;
        b -= a;
        b ^= (a << 9);
        c -= a;
        c -= b;
        c ^= (b >> 8);
        a -= b;
        a -= c;
        a ^= (c >> 38);
        b -= c;
        b -= a;
        b ^= (a << 23);
        c -= a;
        c -= b;
        c ^= (b >> 5);
        a -= b;
        a -= c;
        a ^= (c >> 35);
        b -= c;
        b -= a;
        b ^= (a << 49);
        c -= a;
        c -= b;
        c ^= (b >> 11);
        a -= b;
        a -= c;
        a ^= (c >> 12);
        b -= c;
        b -= a;
        b ^= (a << 18);
        c -= a;
        c -= b;
        c ^= (b >> 22);
        /* mix64(a, b, c); */

        return c;
	 }
	 
	private static final long uintToLong(final int i) 
		{
        long l = (long) i;
        return (l << 32) >>> 32;
		}
	 
	 private static final int gatherIntLE(final byte[] data, final int index) 
		 {
		 int i = data[index] & 0xFF;

		 i |= (data[index + 1] & 0xFF) << 8;
		 i |= (data[index + 2] & 0xFF) << 16;
		 i |= (data[index + 3] << 24);

		 return i;
		 }
	 
	 private static final long gatherLongLE(final byte[] data, final int index) 
		 {
		 int i1 = gatherIntLE(data, index);
		 long l2 = gatherIntLE(data, index + 4);

		 return uintToLong(i1) | (l2 << 32);
		 }

	 private static final long gatherPartialLongLE(final byte[] data, final int index, final int available) 
		 {
		 if(available >= 4) {
            int i = gatherIntLE(data, index);
            long l = uintToLong(i);

            int left = available - 4;

            if(left == 0) {
                return l;
            }

            int i2 = gatherPartialIntLE(data, index + 4, left);

            l <<= (left << 3);
            l |= (long) i2;

            return l;
		 } 
		 else 
        	{
            return (long) gatherPartialIntLE(data, index, available);
        	}
		 }
	 
	  private static final int gatherPartialIntLE(final byte[] data, final int index, final int available) 
		  {
        int i = data[index] & 0xFF;

        if(available > 1) {
            i |= (data[index + 1] & 0xFF) << 8;
            if(available > 2) {
                i |= (data[index + 2] & 0xFF) << 16;
            }
        }

        return i;
		  }
	}

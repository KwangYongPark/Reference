package dev.binz.reference;

public @interface Crypto {
	CryptoEnum crypto() default CryptoEnum.DEFAULT ;
}

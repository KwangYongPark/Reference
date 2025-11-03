package dev.binz.reference;

import java.lang.reflect.Field;

public class Dto {
	private void name(Object body) {
		String errorCode = "";
		String trid = "";
		String type = "";
		String ver = "";
		String data = "";
		try {
			Field[] fields = body.getClass().getDeclaredFields();
			for(Field f : fields) {
				if(f.isAnnotationPresent(Crypto.class)) {
					Crypto crypto = f.getAnnotation(Crypto.class);
					f.setAccessible(true);
					String value = f.get(body) == null ? "" : f.get(body).toString();
					if(value.isBlank()) {
						errorCode = "["+f.getName()+"] 공백일 수 없습ㄴ디ㅏ.";
					}
					switch (crypto.crypto()) {
					case TRID:
						trid = value;
						if(value.isBlank()) {
							errorCode = "["+f.getName()+"] 공백일 수 없습ㄴ디ㅏ.";
						}
						break;
					case TYPE:
						type = value;
						if(value.isBlank()) {
							errorCode = "["+f.getName()+"] 공백일 수 없습ㄴ디ㅏ.";
						}
						break;
					case VERSION:
						ver = value;
						if(value.isBlank()) {
							errorCode = "["+f.getName()+"] 공백일 수 없습ㄴ디ㅏ.";
						}
						break;
					case DATA:
						data = value;
						if(value.isBlank()) {
							errorCode = "["+f.getName()+"] 공백일 수 없습ㄴ디ㅏ.";
						}
						break;

					default:
						break;
					}
				}
			}
			
			if(trid == null || trid.isBlank()) {
				System.out.println("ERROR");
			}else if(!errorCode.isBlank()) {
				System.out.println(errorCode);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

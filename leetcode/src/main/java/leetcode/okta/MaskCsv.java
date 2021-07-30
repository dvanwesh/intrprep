package leetcode.okta;

// you can also use imports, for example:
// import java.util.*;
/*
    .abc@abc.com
    ****@abc.com

    2) abc@abc@abc.com,k@t.com
    abc@abc@abc.com
    *******@abc.com
*/
public class MaskCsv {

	public static void main(String [] args) {
		// you can write to stdout for debugging purposes, e.g.
		String str ="john,a@b,sam@ab.c,@abc.com,joe@,,sam.joe@sam.joe";
		MaskCsv sol = new MaskCsv();
		System.out.println(sol.maskInput(str));
		System.out.println(sol.maskInput(str.toCharArray()));
		System.out.println(sol.maskInput(".abc@abc.com"));
		System.out.println(sol.maskInput("abc@abc@abc.com,k@t.com"));
	}
	// O(n) n: len of input string, space : O(n);
	private String maskInput(char[] emails){
		boolean canMaskInp = true;
		for(int i = 0; i < emails.length; i++){
			if(emails[i] == '@' || emails[i] == ','){
				canMaskInp = emails[i] == ',';
				continue;
			}
			if(canMaskInp){
				emails[i] = '*';
			}
		}
		return new String(emails);
	}
	private String maskInput(String str){
		String maskedInp = "";
		String[] emails = str.split(",");
		for(int i = 0; i < emails.length; i++){
			String email = emails[i];
			int idx = email.indexOf('@');
			idx = idx == -1 ? email.length() : idx;
			maskedInp += createMaskedStr(idx) + email.substring(idx) +
					((i < emails.length-1) ? "," : "");
		}
		return maskedInp;
	}

	private String createMaskedStr(int idx){
		String mask = "";
		while(idx-- > 0){
			mask += "*";
		}
		return mask;
	}
}


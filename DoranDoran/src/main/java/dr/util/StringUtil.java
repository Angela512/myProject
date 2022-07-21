package dr.util;

public class StringUtil {
	//HTML태그를 허용하면서 줄바꿈
	public static String useBrHtml(String str) {
		if(str == null) return null;
		
		return str.replaceAll("\r\n", "<br>")
				  .replaceAll("\r", "<br>")
				  .replaceAll("\n", "<br>");  //html허용하면서 줄바꿈하게끔 설정
	}
	
	//HTML태그를 허용하지 않으면서 줄바꿈
	public static String useBrNoHtml(String str) {
		if(str == null) return null;
		
		return str.replaceAll("<", "&lt;")  //태그 명시하는 <를 $lt;하면 태그 무력화, 일반 문자로.
			      .replaceAll(">", "&gt;")
			      .replaceAll("\r\n", "<br>")
			      .replaceAll("\r", "<br>")
			      .replaceAll("\n", "<br>");
	}
	
	//HTML을 허용하지 않음
	public static String useNoHtml(String str) {
		if(str == null) return null;
		
		return str.replaceAll("<", "&lt;")
				  .replaceAll(">", "&gt;");
	}
	
	//문자열을 지정한 문자열 개수 이후에 ...으로 처리
	public static String shortWords(int length,String content) {
		if(content == null) return null;
		
		if(content.length() > length) {
			return content.substring(0,length) + " ...";
		}
		
		return content;
	}
	
	
}

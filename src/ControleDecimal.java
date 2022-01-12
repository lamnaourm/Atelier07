import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class ControleDecimal extends PlainDocument{
	
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		// TODO Auto-generated method stub
		try {
			String num = getText(0,getLength()) + str;
			Double.parseDouble(num);
			super.insertString(offs, str, a);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}

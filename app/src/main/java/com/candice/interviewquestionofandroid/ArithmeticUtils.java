package com.candice.interviewquestionofandroid;

/**
 * <br>
 * function:
 * <p>
 *
 * @author:Lei
 * @date:2019/3/14 下午1:52
 * @since:V$VERSION
 * @desc:com.candice.interviewquestionofandroid
 */
public class ArithmeticUtils {


	//check palindrome
	public static boolean isPalindrome( String word ) {
		word = word.toLowerCase();
		int m = word.length();
		for (int i = 0; i < m; i++, m--) {
			if ( word.charAt( i ) != word.charAt( m - 1 ) ) {
				return false;
			}
		}
		return true;
	}


	public static String longestPalindrome( String str ) {
		//store  word and longWord
		String longestWord = "", word = "";
		//word length
		int length, length1 = 0;
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt( i );
			//get a word
			if ( ch != ' ' ) {
				word = word + ch;
			} else {
				length = word.length();
				if ( isPalindrome( word ) && length > length1 ) {
					length1 = length;
					longestWord = word;
				}
				word = "";
			}
		}
		return longestWord;
	}


	public static ListPoint getListNode( ListPoint listPointA, ListPoint listPointB ) {
		if ( listPointA == null || listPointB == null ) {
			return null;
		}

		int lenA = getLen( listPointA );
		int lenB = getLen( listPointB );

		if ( lenA > lenB ) {
			while ( lenA > lenB ) {
				listPointA = listPointA.next;
				lenA--;
			}
		} else {
			while ( lenA < lenB ) {
				listPointB = listPointB.next;
				lenB--;
			}
		}
		while ( listPointA != null ) {
			if ( listPointA == listPointB ) {
				return listPointA;
			}
			listPointA = listPointA.next;
			listPointB = listPointB.next;
		}
		return null;
	}

	private static int getLen( ListPoint listPoint ) {
		int len = 0;
		while ( listPoint != null ) {
			len++;
			listPoint = listPoint.next;
		}
		return len;
	}

}

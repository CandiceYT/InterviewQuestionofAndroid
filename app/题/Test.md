1.code 

```kotlin
//check palindrome
fun String.isPalindrome(): Boolean {
	var word = this
	word = word.toLowerCase()
	var m = word.length
	var i = 0
	while (i < m) {
		if (word[i] != word[m - 1]) {
			return false
		}
		i++
		m--
	}
	return true
}


fun String.longestPalindrome(): String {
	//store  word and longWord
	var longestWord = ""
	var word = ""
	//word length
	var length: Int
	var length1 = 0
	for (i in 0 until this.length) {
		val ch = this[i]
		//get a word
		if (ch != ' ') {
			word += ch
		} else {
			length = word.length
			if (isPalindrome(word) && length > length1) {
				length1 = length
				longestWord = word
			}
			word = ""
		}
	}
	return longestWord
}
```



2.code

```java
public static ListPoint getListPoint( ListPoint listPointA, ListPoint listPointB ) {
		if ( listPointA == null || listPointB == null ) {
			return null;
		}
        //get link list length
		int lenA = getLen( listPointA );
		int lenB = getLen( listPointB );

        //execute until two link list equal
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

public class ListPoint {
	int node;
	ListPoint next;

	ListPoint( int node ) {
		this.node = node;
		next = null;
	}
}

```


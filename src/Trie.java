import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isEndOfWord;

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }
}

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.getChildren().computeIfAbsent(c, key -> new TrieNode());
            current = current.getChildren().get(c);
        }
        current.setEndOfWord(true);
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!current.getChildren().containsKey(c)) {
                return false;
            }
            current = current.getChildren().get(c);
        }
        return current.isEndOfWord();
    }

    public ArrayList<String> autoComplete(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            if (!current.getChildren().containsKey(c)) {
                return new ArrayList<>();
            }
            current = current.getChildren().get(c);
        }
        return getAllWords(current, prefix);
    }

    private ArrayList<String> getAllWords(TrieNode node, String prefix) {
        ArrayList<String> words = new ArrayList<>();
        if (node.isEndOfWord()) {
            words.add(prefix);
        }
        for (char c : node.getChildren().keySet()) {
            TrieNode childNode = node.getChildren().get(c);
            words.addAll(getAllWords(childNode, prefix + c));
        }
        return words;
    }
}

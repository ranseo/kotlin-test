package programmers

import java.util.*

class SolutionFindLyrics {
    fun solution(words: Array<String>, queries: Array<String>): List<Int> {

        var answer = mutableListOf<Int>()

        val trie: MutableMap<Int, Trie> = mutableMapOf()
        val reverseTrie: MutableMap<Int, Trie> = mutableMapOf()

        for (word in words) {
            val idx = word.length
            trie[idx]?.addNode(word) ?: trie.put(idx, Trie().also { it.addNode(word)})
            reverseTrie[idx]?.addNode(word.reversed()) ?: reverseTrie.put(idx, Trie().also { it.addNode(word.reversed())})
        }


        for (query in queries) {
            val idx = query.length
            val new = query.replace(Regex("[?]+"), "")

            if (new.length == 0) {
                answer.add(trie[idx]?.rootNode?.count ?: 0)
            } else if (query[0] == '?') {
                answer.add(reverseTrie[idx]?.let { it.getNode(new.reversed())?.count ?: 0} ?:0)
            } else {
                answer.add(trie[idx]?.let{ it.getNode(new)?.count ?: 0} ?: 0)
            }
        }

        return answer
    }

    class TrieNode {
       
        val childNode: MutableMap<Char, TrieNode> = mutableMapOf()
        var count: Int = 1
    }

    class Trie {
        val rootNode = TrieNode()

        init {
            this.rootNode.count = 0
        }

        fun addNode(word: String) {
            var tempNode = this.rootNode
            rootNode.count++

            for (char in word) {
                val tempChildNode = tempNode.childNode[char]
                tempNode = tempChildNode?.apply { count++ } ?: tempNode.childNode.computeIfAbsent(char) { TrieNode() }
            }
        }

        fun getNode(word: String): TrieNode? {
            var tempNode = this.rootNode

            for (char in word) {
                val node = tempNode.childNode[char] ?: return null
                tempNode = node
            }

            return tempNode
        }
    }
}

fun main() {
    val solutionFindLyrics = SolutionFindLyrics()
    val words = arrayOf("frodo", "front", "frost", "frozen", "frame", "kakao")
    val queries = arrayOf("fro??", "????o", "fr???", "fro???", "pro?")

    println(solutionFindLyrics.solution(words, queries))

}
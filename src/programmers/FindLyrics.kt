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

class SolutionFindLyrics2 {
    fun solution(words: Array<String>, queries: Array<String>): ArrayList<Int> {
        var answer = arrayListOf<Int>()

        //words 제한사항 -> words사이즈는 100_000, words의 원소 사이즈는 10_000 = 벌써 10억 선넘쥬? 따라서 완전탐색x
        //중복은 없고, 알파벳 소문자로만 이루어져있다. -> queries도 마찬가지

        //queries 제한사항 -> 검색키워드 중복 가능, 반드시 '?'가 하나 이상포함, 접두사 또는 접미사로만 주어짐. 중간x

        //Trie자료구조를 사용하여 words에 주어진 단어들을 add시킨다.
        //이 후 queries에 주어진 검색어를 가지고 Trie자료구조를 탐색하면 해당 검색어에 걸치는 단어가 몇개 있는지 반환.

        //중요한 점은, '?'가 접두,접미에 붙는다는 거. 접미에 붙으면 상관없는데, 접두에 붙을 경우 뒤에 단어로 검색해야 하기 때문에 Trie구조에 words에 주어진 단어들을 거꾸로 넣어야 한다. 따라서 총 두개의 Trie 클래스가 필요.

        //+ words의 크기 별로 trie를 만드는게 좋다. 찾기 빠르게.

        val trie = hashMapOf<Int,Trie>()
        val trie_R = hashMapOf<Int,Trie>()

        words.forEach{ word ->
            val len = word.length
            trie[len]?.addNode(word) ?: trie.put(len, Trie().also{it.addNode(word)})
            trie_R[len]?.addNode(word.reversed()) ?: trie_R.put(len,Trie().also{it.addNode(word.reversed())})
        }

        queries.forEach{ query ->
            //"?"없애기
            val word = query.replace(Regex("[?]+"), "")
            val len = query.length

            //접두 -> reverse

            if(query[0] == '?') trie_R[len]?.getNode(word.reversed())?.also{answer.add(it.count)} ?: answer.add(0)
            else trie[len]?.getNode(word)?.also{answer.add(it.count)} ?: answer.add(0)
        }



        return answer
    }

    class TrieNode{
        val childNode : HashMap<Char,TrieNode> = hashMapOf()
        var count = 0
    }

    class Trie{
        val rootNode = TrieNode()

        init{
            rootNode.count = 0
        }

        fun addNode(word:String) {
            var tmpNode = this.rootNode
            this.rootNode.count++

            for(c in word) {
                val tmpChildNode = tmpNode.childNode[c]
                tmpNode = tmpChildNode?.also{it.count++} ?: tmpNode.childNode.computeIfAbsent(c){TrieNode().also{it.count++}}
            }
        }

        fun getNode(word:String) : TrieNode {
            var tmpNode = this.rootNode

            for(c in word) {
                val tmpChildNode = tmpNode.childNode[c] ?: return TrieNode()
                tmpNode = tmpChildNode
            }
            return tmpNode
        }
    }
}
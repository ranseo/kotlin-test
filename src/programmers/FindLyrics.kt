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

        //words ???????????? -> words???????????? 100_000, words??? ?????? ???????????? 10_000 = ?????? 10??? ?????????? ????????? ????????????x
        //????????? ??????, ????????? ??????????????? ??????????????????. -> queries??? ????????????

        //queries ???????????? -> ??????????????? ?????? ??????, ????????? '?'??? ?????? ????????????, ????????? ?????? ??????????????? ?????????. ??????x

        //Trie??????????????? ???????????? words??? ????????? ???????????? add?????????.
        //??? ??? queries??? ????????? ???????????? ????????? Trie??????????????? ???????????? ?????? ???????????? ????????? ????????? ?????? ????????? ??????.

        //????????? ??????, '?'??? ??????,????????? ???????????? ???. ????????? ????????? ???????????????, ????????? ?????? ?????? ?????? ????????? ???????????? ?????? ????????? Trie????????? words??? ????????? ???????????? ????????? ????????? ??????. ????????? ??? ????????? Trie ???????????? ??????.

        //+ words??? ?????? ?????? trie??? ???????????? ??????. ?????? ?????????.

        val trie = hashMapOf<Int,Trie>()
        val trie_R = hashMapOf<Int,Trie>()

        words.forEach{ word ->
            val len = word.length
            trie[len]?.addNode(word) ?: trie.put(len, Trie().also{it.addNode(word)})
            trie_R[len]?.addNode(word.reversed()) ?: trie_R.put(len,Trie().also{it.addNode(word.reversed())})
        }

        queries.forEach{ query ->
            //"?"?????????
            val word = query.replace(Regex("[?]+"), "")
            val len = query.length

            //?????? -> reverse

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
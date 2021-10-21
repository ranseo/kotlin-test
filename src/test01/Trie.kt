package test01


//TrieNode 클래스. Trie자료구조에 쓰이는 노드로, 다음 연결될 char와 childNode(=자식 Trie노드) 변수가 담겨있다.
//custom으로 다른 변수를 넣어, 현재 단어의 개수가 몇개인지 확인도 가능
class TrieNode{
    val childNode : MutableMap<Char,TrieNode> = mutableMapOf()
    //var count : Int = 0
}

class Trie{
    val rootNode = TrieNode()

//    init{
//        this.rootNode.count = 0
//    }

    fun addNode(word:String) {
        var tmpNode = this.rootNode
        //rootNode.count++

        //문자열을 char 단위로 for문을 돌려서 자식노드를 (자식노드의 자식노드까지 n번) 만들어준다. -> 트리를 만든다.
        for(c in word) {
            val tmpChildNode = tmpNode.childNode[c]
            tmpNode = tmpChildNode ?: tmpNode.childNode.computeIfAbsent(c){TrieNode()}

            // tmpNode = tmpChildNode?.apply{count++} ?: tmpNode.childNode.computeIfAbsent(c){TrieNode()}
        }
    }

    fun getNode(word:String) : TrieNode? {
        var tmpNode = this.rootNode

        for (c in word) {
            val tmpChildNode = tmpNode.childNode[c] ?: return null
            tmpNode = tmpChildNode
        }

        return tmpNode
    }
}


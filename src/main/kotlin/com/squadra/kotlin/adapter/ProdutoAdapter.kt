package com.squadra.kotlin.adapter

import com.squadra.kotlin.TO.ProdutoTO
import com.squadra.kotlin.model.Produto
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class ProdutoAdapter{

    fun produtoParaTO(produto: Produto): ProdutoTO {
        var produtoTO = ProdutoTO(produto.id, produto.preco, produto.nome, produto.descricao, formatarData(produto.dataValidade),
                produto.promocional
        )
        produtoTO.promocional = validade(produto.dataValidade)
        if (produtoTO.promocional) {
            produtoTO.preco = produtoTO.preco - produtoTO.preco * 15 / 100
        }
        return produtoTO
    }

    fun listaProdutosParaTO(produtos: List<Produto>) : List<ProdutoTO> {
        var produtosTO : MutableList<ProdutoTO> = ArrayList()
        produtos.forEach{ produto ->  produtosTO.add(produtoParaTO(produto)) }
        return produtosTO
    }

    private fun validade(dataValidade: LocalDate): Boolean {
        val dataAtual: LocalDate = LocalDate.now()
        val dias: Period = Period.between(dataValidade, dataAtual)
        return (dataValidade >= dataAtual) && ((dias.days < 7) && (dias.months == 0))
    }

    private fun formatarData(data: LocalDate): String {
        val sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return data.format(sdf)
    }
}
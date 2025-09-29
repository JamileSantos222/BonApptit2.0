package br.com.etecia.epictask.controller;

import br.com.etecia.epictask.model.Produto;
import br.com.etecia.epictask.model.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    // Exibir lista de produtos
    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", repository.findAll());
        return "produtos"; // vai abrir produtos.html
    }

    // Exibir formulário de cadastro
    @GetMapping("/produtos/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "cadastro"; // vai abrir cadastro.html
    }

    // Salvar produto
    @PostMapping("/produtos")
    public String salvarProduto(Produto produto) {
        repository.save(produto);
        return "redirect:/produtos";
    }
}

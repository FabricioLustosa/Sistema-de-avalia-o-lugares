# 🚀 Relatório Técnico de Melhorias - Projeto Avaliação de Lugares

Este documento detalha as intervenções técnicas realizadas no projeto para garantir estabilidade, clareza e fins didáticos.

---

## 1. Correção do Fluxo de Dados (DTOs)
### Problema
O sistema utilizava entidades diretamente na camada de controle em alguns pontos, ou DTOs incompletos. Isso causava o envio de dados desnecessários ou campos nulos (como a `description`).
### Solução
*   **Mapeamento Explícito:** No `PlaceResponseDTO`, implementamos a captura manual de cada campo da entidade `Place`.
*   **Proteção de Recursão:** Ao adicionar a lista de reviews no DTO de resposta, utilizamos o `ReviewResponseDTO`. Isso evita que o JSON tente renderizar o `Place` dentro do `Review`, que por sua vez renderizaria os `Reviews` novamente, causando um estouro de memória (Stack Overflow) no Jackson/Spring.

---

## 2. Robustez do PlaceService
### Melhoria: `deleteReview`
*   **Antes:** O método de deleção de reviews era simplista.
*   **Depois:** Adicionamos uma verificação defensiva que primeiro valida a existência do `Place` e então percorre de forma segura a lista de `Reviews` para encontrar o alvo exato antes de invocar o `reviewRepository.delete()`. Isso garante que não tentemos deletar dados órfãos ou inexistentes.

---

**Assinado:** Araken13
**Data:** 28 de Abril de 2026

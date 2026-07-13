# Ajustes de refinamento no redesign

## 1. Botão de logout
O botão de logout está visualmente pobre e desalinhado com o novo design:
- Estilize-o de forma consistente com os demais botões do sistema (mesma família de estilos, cantos arredondados, transição no hover)
- Como é uma ação de saída, trate-o como ação secundária/discreta: variante outline ou ghost, podendo usar um tom neutro ou vermelho suave no hover para indicar "sair"
- Se estiver na navbar, alinhe-o corretamente com os demais itens, com espaçamento adequado

## 2. Espaçamento após o último card
Há pouco respiro entre o último card da listagem e o rodapé/elementos seguintes (incluindo a área do botão de logout):
- Aumente o espaçamento inferior da seção de cards (margin/padding-bottom generoso, ex: 48-64px)
- Garanta que esse respiro exista em todas as telas com listagens, não só na principal

## 3. Fundo geral
O fundo está claro demais e quase não se distingue das superfícies brancas:
- Escureça um ou dois tons o neutro do fundo (ex: de #f6f7f9 para algo como #eef0f3) para que os cards brancos se destaquem com clareza
- Ajuste apenas a variável CSS do fundo — as superfícies/cards permanecem brancos
- Confira que o contraste entre fundo e cards ficou perceptível mas ainda sutil, sem parecer cinza pesado
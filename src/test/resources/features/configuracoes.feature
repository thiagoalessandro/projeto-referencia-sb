# language: pt

Funcionalidade: Gerenciando configurações

  Cenario: Cadastrando configuracao valida
    Dada uma configuracao valida
    Quando a configuracao valida e cadastrada
    Entao a configuracao valida e salva

  Cenario: Cadastrando configuracao duplicada
    Dada uma configuracao duplicada
    Quando a configuracao duplicada e cadastrada
    Entao a configuracao duplicada e rejeitada




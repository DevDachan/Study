# 1. NginX κ°λ

[[WEB] π Reverse Proxy / Forward Proxy μ μ & μ°¨μ΄ μ λ¦¬](https://inpa.tistory.com/entry/NETWORK-π‘-Reverse-Proxy-Forward-Proxy-μ μ-μ°¨μ΄-μ λ¦¬)

## NginXλ?

- κΈ°λ³Έμ μΌλ‘ apacheμ κ°μ μΉ μλ² μ€ νλλ‘μ¨ κ°λ²Όμ°λ©΄μ μ¬λ¬ μμ²­μ νλ²μ μ²λ¦¬ν  μ μλ μ₯μ μ κ°μ§κ³  μλ μΉ μλ²μ΄λ€.
- Apacheλ³΄λ€λ λμμ΄ λ¨μνκ³  μ λ¬μ μ­ν λ§ νκΈ°μ λμ μ μμ νΉνλμ΄ μμ
- κ΅¬λ λ°©μμ **Event Driven(λΉλκΈ°μ²λ¦¬λ°©μ)**μ΄μ΄μ μμ²­μ΄ λ€μ΄μ€λ©΄ μ΄λ€ λμμ ν΄μΌνλμ§λ§ μλ €μ£Όκ³  λ€λ₯Έ μμ²­μ μ²λ¦¬νλ λ°©μμ΄λ€.

## μΉ μλ²μ μ­ν 

- μΉ μλ²μ μ­ν μ HTML, CSS, JavaScript, μ΄λ―Έμ§μ κ°μ μ μ μΈ μ λ³΄λ₯Ό μ¬μ©μ(μΉ λΈλΌμ°μ )μκ² μ μ‘ν΄μ£Όλ μ­ν μ νλ€.
- Reverse Proxy(λ¦¬λ²μ€ νλ‘μ)μ μ­ν μ νμ¬ μ¬μ©μμ μμ²­μ μλ²λ¨μΌλ‘ λ°μ΄μ£Όλ μ­ν μ νλ€.

## Proxy(νλ‘μ)

- νλ‘μ μλ²λ ν΄λΌμ΄μΈνΈκ° μμ μ ν΅ν΄μ λ€λ₯Έ λ€νΈμν¬ μλΉμ€μ κ°μ μ μΌλ‘ μ μν  μ μκ² ν΄μ£Όλ μ»΄ν¨ν° μμ€νμ΄λ μμ©νλ‘κ·Έλ¨μ κ°λ¦¬ν¨λ€.
- νλ‘μλ βλλ¦¬βλΌλ μλ―Έλ₯Ό κ°κ³  μμΌλ©° μλ²μ μλ² μ¬μ΄μ μ€κ³μ μ­ν μ νλ€κ³  μκ°νλ©΄ λλ€.
- **Forward Proxy:**
    - ν΄λΌμ΄μΈνΈμ λ°λ‘ λ€μ λμ¬ μμ΄ κ°μ λ΄λΆλ§μ μ‘΄μ¬νλ ν΄λΌμ΄μΈνΈμ μμ²­μ λ°μ μΈν°λ·μ ν΅ν΄ μΈλΆ μλ²μμ λ°μ΄ν°λ₯Ό κ°μ Έμ μλ΅μ ν΄μ€λ€.
    - μ¦ ν΄λΌμ΄μΈνΈκ° μλ²μ μ κ·Όνκ³ μ ν  λ ν΄λΌμ΄μΈνΈλ νκ² μλ²μ μ£Όμλ₯Ό ν¬μλ νλ‘μμ μ λ¬νκ³  ν¬μλ νλ‘μκ° μΈν°λ·μΌλ‘ μμ²­λ λ΄μ©μ κ°μ Έμ€λ λ°©μμ΄λ€.
        

        <img src="https://user-images.githubusercontent.com/111109411/221348189-2cb80284-955e-460c-9f7b-c28d4dad7d60.png" width=80%>

        
    - **ν΄λΌμ΄μΈνΈ λ³΄μ:** ν¬μλ νλ‘μ μλ²λ λ°©νλ²½κ³Ό κ°μ κ°λμΌλ‘ μ νμ μν΄ μ¬μ©νμ¬ νΉμ  μ¬μ©μλ€μ΄ μΉμ¬μ΄νΈμ μ§μ μ μΌλ‘ λ°©λ¬Ένλ κ²μ λ§μ μ μλ€..
    - **μΊμ±**: νλ‘μ μλ²λ μ°λ¦¬κ° νΉμ  μΉ νμ΄μ§μ μ μνμ λ νμ΄μ§ μλ²μ μ λ³΄λ₯Ό μΊμ±(μμ λ³΄κ΄)ν΄λλ€. κ·Έλ κΈ°μ μλ²μ λΆνλ₯Ό μ€μ΄λ ν¨κ³Όλ λΌ μ μλ€.
    - **μνΈν**: ν΄λΌμ΄μΈνΈμ μμ²­μ ν¬μλ νλ‘μ μλ²λ₯Ό ν΅κ³Όν  λ μνΈνλλ€.
- **Reverse Proxy:**
    - λ¦¬λ²μ€ νλ‘μλ μΉμλ²/WAS μμ λμ¬ ν΄λΌμ΄μΈνΈκ° μΉμλΉμ€μ μ κ·Όν  λ μΉ μλ²μ μμ²­μ νλ κ²μ΄ μλ νλ‘μλ‘ μμ²­νκ² λκ³  νλ‘μκ° λ°°ν( Reverse)μ μλ²λ‘λΆν° λ°μ΄ν°λ₯Ό κ°μ Έμ€λ λ°©μμ΄λ€.
    - ν΄λΌμ΄μΈνΈ μͺ½μΌλ‘ λ°μ΄ν°λ₯Ό λ°μ΄μ£Όλ κ² ν¬μλ νλ‘μλΌλ©΄ λ°λλ‘ μλ²μͺ½μΌλ‘ λ°μ΄ν°λ₯Ό λ°μ΄μ£Όλ κ²μ λ¦¬λ²μ€ νλ‘μλΌκ³  μκ°νλ©΄ λλ€.
        
        <img src="https://user-images.githubusercontent.com/111109411/221348190-7820419e-d6da-494b-abe1-4c88d75b3118.png" width=80%>

        
        
    - **λ‘λ λ°Έλ°μ±**: μλ§μ μ¬μ©μμ νΈλν½μ νλμ μ±κΈ μλ²λ‘ κ°λΉνκΈ°λ νλ€λ€. λλ¬Έμ λ¦¬λ²μ€ νλ‘μ μλ²λ₯Ό μ¬λ¬κ°μ λ³Έ μλ² μμ λλ©΄ νΉμ  μλ²κ° κ³ΌλΆν λμ§ μκ² λ‘λ λ°Έλ°μ±μ΄ κ°λ₯νλ€.
    - **μλ² λ³΄μ:** λ¦¬λ²μ€ νλ‘μλ₯Ό μ¬μ©νκ² λλ©΄ λ³Έλ μλ²μ IPμ£Όμλ₯Ό λΈμΆμν€μ§ μμ μ μκΈ° λλ¬Έμ DDos κ³΅κ²©κ³Ό κ°μ λ³΄μ κ³΅κ²©μ λ§λλ° μ μ©νλ€.
    - **μΊμ±**: ν¬μλ νλ‘μμ λΉμ·νκ² μΊμ± κΈ°λ₯μ΄ κ°λ₯
    - **μνΈν**: μ¬μ©μμκ² λ€μ΄μ€λ μμ²­μ λͺ¨λ λ³΅νΈννκ³  λκ°λ μλ΅μ μνΈν ν΄μ£Όλ―λ‘ ν΄λΌμ΄μΈνΈμ μμ ν ν΅μ μ ν  μ μμΌλ©° λ³Έλ μλ²μ λΆλ΄μ μ€μ¬μ€ μ μλ€.

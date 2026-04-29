import requests
import json
import time

def processamento_local():
    # URLs dos seus microserviços
    URL_REQUISICOES = "http://localhost:8083/requisicoes"
    URL_PROJETOS = "http://localhost:8084/projetos-lei"

    print("🛠️ Iniciando Modo de Teste Local (Sem IA Externa)")

    # 1. Coleta os dados reais do 8083
    try:
        response = requests.get(URL_REQUISICOES)
        requisicoes = response.json()
        print(f"📥 {len(requisicoes)} requisições coletadas do 8083.")
    except Exception as e:
        print(f"❌ Erro ao conectar no 8083: {e}")
        return

    # 2. Simula a síntese (Agrupa e cria Projetos de Lei fictícios)
    print("🧠 Simulando síntese de dados...")
    time.sleep(1) # Simula um delay de processamento
    
    # Criamos 2 projetos baseados no que costuma vir das vozes do povo
    projetos_mock = [
        {
            "identificador": "PL-LOCAL/2026-01",
            "titulo": "Melhoria da Infraestrutura Urbana",
            "ementa": "Consolidação de todas as queixas sobre saneamento e asfalto.",
            "responsavel": "MOCK-IA",
            "voto": "AGUARDANDO_VOTACAO"
        },
        {
            "identificador": "PL-LOCAL/2026-02",
            "titulo": "Plano Emergencial de Segurança",
            "ementa": "Resposta automatizada às demandas de patrulhamento.",
            "responsavel": "MOCK-IA",
            "voto": "AGUARDANDO_VOTACAO"
        }
    ]

    # 3. Envia os resultados para o 8084
    print(f"⚖️ Enviando {len(projetos_mock)} projetos para o 8084...")
    for projeto in projetos_mock:
        try:
            res = requests.post(URL_PROJETOS, json=projeto)
            if res.status_code in [200, 201]:
                print(f"✅ Projeto '{projeto['titulo']}' enviado com sucesso!")
            else:
                print(f"❌ Erro ao enviar: {res.status_code}")
        except Exception as e:
            print(f"❌ Falha de conexão com 8084: {e}")

if __name__ == "__main__":
    processamento_local()

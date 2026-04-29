import { useState, useEffect } from 'react';
import axios from 'axios';

interface EnteALL {
  identification: string;
  description: string;
  created: string;
  attributes: string[];
}

const api = axios.create({
  baseURL: 'http://localhost:8080'
});

function App() {
  const [tab, setTab] = useState<'dash' | 'add'>('dash');
  const [entes, setEntes] = useState<EnteALL[]>([]);
  const [valorInput, setValorInput] = useState('');
  const [loading, setLoading] = useState(false);

  const fetchEntes = async () => {
    try {
      const response = await api.get('/creators');
      setEntes(response.data);
    } catch (error) {
      console.error("Erro na base Genesis:", error);
    }
  };

  useEffect(() => {
    fetchEntes();
  }, []);

  const handleProtocolar = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!valorInput.trim()) return;

    setLoading(true);
    try {
      // Enviando como "nome" para bater com o teste de carga de sucesso
      await api.post('/creators', { nome: valorInput });
      
      setValorInput('');
      await fetchEntes();
      setTab('dash');
    } catch (error) {
      console.error("Erro ao protocolar:", error);
      alert("Erro na sincronização.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 font-sans">
      <nav className="bg-slate-900 border-b border-slate-800 p-4 sticky top-0 z-10">
        <div className="max-w-6xl mx-auto flex justify-between items-center">
          <div className="flex items-center gap-8">
            <h1 className="text-xl font-black text-emerald-500 tracking-tighter uppercase">Gov.OS</h1>
            <div className="flex bg-slate-800 p-1 rounded-xl">
              <button 
                onClick={() => setTab('dash')} 
                className={`px-4 py-1.5 rounded-lg text-sm font-bold transition ${tab === 'dash' ? 'bg-emerald-600 text-white shadow-lg' : 'text-slate-400 hover:text-white'}`}
              >
                Dashboard
              </button>
              <button 
                onClick={() => setTab('add')} 
                className={`px-4 py-1.5 rounded-lg text-sm font-bold transition ${tab === 'add' ? 'bg-emerald-600 text-white shadow-lg' : 'text-slate-400 hover:text-white'}`}
              >
                Inserir
              </button>
            </div>
          </div>
          <div className="text-[10px] font-mono text-slate-500 border border-slate-800 px-3 py-1 rounded-full bg-slate-900">
            TOTAL_SYNC: {entes.length.toLocaleString()}
          </div>
        </div>
      </nav>

      <main className="p-6 max-w-6xl mx-auto">
        {tab === 'dash' ? (
          <div className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <div className="bg-slate-900 border border-slate-800 p-5 rounded-2xl">
                <p className="text-xs font-bold text-slate-500 uppercase">Status do Ente</p>
                <p className="text-2xl font-black mt-1">Ativo</p>
              </div>
              <div className="bg-slate-900 border border-slate-800 p-5 rounded-2xl">
                <p className="text-xs font-bold text-slate-500 uppercase">Conexões</p>
                <p className="text-2xl font-black mt-1 text-emerald-500">{entes.length.toString().padStart(2, '0')}</p>
              </div>
              <div className="bg-slate-900 border border-slate-800 p-5 rounded-2xl">
                <p className="text-xs font-bold text-slate-500 uppercase">Última Iteração</p>
                <p className="text-2xl font-black mt-1">{entes.length > 0 ? 'AGORA' : 'S/ DADOS'}</p>
              </div>
            </div>

            <div className="bg-slate-900 rounded-2xl border border-slate-800 overflow-hidden shadow-2xl">
              <div className="px-6 py-4 border-b border-slate-800 bg-slate-800/30">
                <h2 className="font-bold text-slate-300 italic">Grande Museu de Objetos ALL</h2>
              </div>
              <div className="divide-y divide-slate-800">
                {entes.map(ente => (
                  <div key={ente.identification} className="px-6 py-4 hover:bg-slate-800/20 transition flex justify-between items-center">
                    <div>
                      <h3 className="font-bold text-lg">{ente.description || "VALOR_NULO"}</h3>
                      <code className="text-[10px] text-slate-500">ID: {ente.identification}</code>
                    </div>
                    <div className="flex gap-2">
                      {ente.attributes && ente.attributes.length > 0 ? ente.attributes.map(a => (
                        <span key={a} className="bg-emerald-500/10 text-emerald-400 text-[10px] px-2 py-1 rounded-md border border-emerald-500/20">REF:{a.substring(0,8)}</span>
                      )) : <span className="text-[10px] text-slate-600 italic">RAIZ_ATÔMICA</span>}
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        ) : (
          <div className="max-w-xl mx-auto bg-slate-900 p-8 rounded-3xl border border-slate-800 shadow-2xl mt-10">
            <h2 className="text-2xl font-black mb-1">Novo Registro</h2>
            <p className="text-slate-500 text-sm mb-8 uppercase tracking-widest font-bold">Camada Genesis</p>
            <form onSubmit={handleProtocolar} className="space-y-6">
              <div className="space-y-2">
                <label className="text-xs font-bold text-slate-500 uppercase ml-1 text-emerald-500">Campo: Nome (description)</label>
                <input 
                  type="text" 
                  value={valorInput}
                  onChange={(e) => setValorInput(e.target.value)}
                  className="w-full bg-slate-950 border border-slate-800 p-4 rounded-xl focus:ring-2 focus:ring-emerald-500 outline-none transition text-emerald-50" 
                  placeholder="Ex: Carga_Genesis_Manual" 
                  required
                  autoFocus
                />
              </div>
              <button 
                type="submit"
                disabled={loading}
                className="w-full py-4 bg-emerald-600 hover:bg-emerald-500 text-white font-black rounded-xl transition-all disabled:opacity-50"
              >
                {loading ? 'SINCRONIZANDO...' : 'PROTOCOLAR NO MUSEU'}
              </button>
            </form>
          </div>
        )}
      </main>
    </div>
  );
}

export default App;

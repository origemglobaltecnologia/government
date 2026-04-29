import { useState, useEffect } from 'react';
import axios from 'axios';

interface Constituicao {
  uuid?: string;
  identificador: string;
  titulo: string;
  tipoNorma: string;
  ementa: string;
  textoOriginal: string;
  artigo: string;
  statusTramitacao: string;
  riscoInconstitucionalidade: number;
  geraDespesa: boolean;
  estimativaCusto: number;
  origemServico: string;
}

const api = axios.create({ baseURL: 'http://localhost:8082' });

const INITIAL_FORM: Constituicao = {
  identificador: '',
  titulo: '',
  tipoNorma: '',
  ementa: '',
  textoOriginal: '',
  artigo: '',
  statusTramitacao: '',
  riscoInconstitucionalidade: 0,
  geraDespesa: false,
  estimativaCusto: 0,
  origemServico: 'WEB-UI'
};

function App() {
  const [tab, setTab] = useState<'dash' | 'form'>('dash');
  const [leis, setLeis] = useState<Constituicao[]>([]);
  const [form, setForm] = useState<Constituicao>(INITIAL_FORM);
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState<string[]>([]);

  const fetchLeis = async () => {
    try {
      const response = await api.get('/constituicao');
      setLeis(Array.isArray(response.data) ? response.data : []);
    } catch (error) { 
      console.error("Erro ao buscar dados:", error); 
    }
  };

  useEffect(() => { fetchLeis(); }, []);

  const validarForm = () => {
    const novosErros: string[] = [];
    if (!form.identificador.trim()) novosErros.push("Identificador é obrigatório.");
    if (!form.titulo.trim()) novosErros.push("Título é obrigatório.");
    if (!form.artigo.trim()) novosErros.push("Número do Artigo é obrigatório.");
    if (!form.ementa.trim()) novosErros.push("A ementa não pode estar vazia.");
    setErrors(novosErros);
    return novosErros.length === 0;
  };

  const handleSalvar = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validarForm()) return;
    setLoading(true);
    try {
      if (form.uuid) {
        await api.put(`/constituicao/${form.uuid}`, form);
      } else {
        await api.post('/constituicao', form);
      }
      setForm(INITIAL_FORM);
      setErrors([]);
      await fetchLeis();
      setTab('dash');
    } catch (error) {
      alert("Falha na integridade dos dados.");
    } finally { setLoading(false); }
  };

  const handleExcluir = async (e: React.MouseEvent, uuid: string) => {
    e.stopPropagation(); // Impede de abrir a edição ao clicar em excluir
    if (!window.confirm("Confirmar exclusão permanente deste protocolo?")) return;
    
    try {
      await api.delete(`/constituicao/${uuid}`);
      await fetchLeis();
    } catch (error) {
      alert("Erro ao excluir registro.");
    }
  };

  const handleEdit = (lei: Constituicao) => {
    setForm(lei);
    setErrors([]);
    setTab('form');
  };

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 font-sans">
      <nav className="bg-slate-900 border-b border-slate-800 p-4 sticky top-0 z-20">
        <div className="max-w-6xl mx-auto flex justify-between items-center">
          <div className="flex items-center gap-8">
            <h1 className="text-xl font-black text-emerald-500 uppercase tracking-tighter">Constituição.OS</h1>
            <div className="flex bg-slate-800 p-1 rounded-xl">
              <button onClick={() => setTab('dash')} className={`px-4 py-1.5 rounded-lg text-sm font-bold ${tab === 'dash' ? 'bg-emerald-600' : 'text-slate-400'}`}>Dashboard</button>
              <button onClick={() => { setForm(INITIAL_FORM); setErrors([]); setTab('form'); }} className={`px-4 py-1.5 rounded-lg text-sm font-bold ${tab === 'form' ? 'bg-emerald-600' : 'text-slate-400'}`}>
                {form.uuid ? 'Editar' : 'Novo'}
              </button>
            </div>
          </div>
          <div className="text-[10px] font-mono text-slate-500 border border-slate-800 px-3 py-1 rounded-full bg-slate-900 uppercase">
            SYNC: {leis.length}
          </div>
        </div>
      </nav>

      <main className="p-6 max-w-6xl mx-auto">
        {tab === 'dash' ? (
          <div className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <div className="bg-slate-900 border border-slate-800 p-5 rounded-2xl">
                <p className="text-[10px] font-bold text-slate-500 uppercase">Status</p>
                <p className="text-2xl font-black mt-1">Ativo</p>
              </div>
              <div className="bg-slate-900 border border-slate-800 p-5 rounded-2xl">
                <p className="text-[10px] font-bold text-slate-500 uppercase">Normas</p>
                <p className="text-2xl font-black mt-1 text-emerald-500">{leis.length.toString().padStart(2, '0')}</p>
              </div>
              <div className="bg-slate-900 border border-slate-800 p-5 rounded-2xl">
                <p className="text-[10px] font-bold text-slate-500 uppercase">DB</p>
                <p className="text-2xl font-black mt-1 text-orange-500">REDIS</p>
              </div>
            </div>

            <div className="bg-slate-900 rounded-2xl border border-slate-800 overflow-hidden shadow-2xl">
              <div className="divide-y divide-slate-800">
                {leis.length > 0 ? leis.map(lei => (
                  <div key={lei.uuid || lei.identificador} onClick={() => handleEdit(lei)} className="px-6 py-5 hover:bg-slate-800/40 transition cursor-pointer flex justify-between items-center group">
                    <div className="flex-1">
                      <div className="flex items-center gap-3">
                        <span className="bg-emerald-500/10 text-emerald-400 text-[10px] font-bold px-2 py-0.5 rounded border border-emerald-500/20">Art. {lei.artigo}</span>
                        <h3 className="font-bold text-lg group-hover:text-emerald-400 transition">{lei.titulo}</h3>
                      </div>
                      <p className="text-xs text-slate-500 mt-1">{lei.ementa}</p>
                    </div>
                    {lei.uuid && (
                      <button 
                        onClick={(e) => handleExcluir(e, lei.uuid!)}
                        className="opacity-0 group-hover:opacity-100 p-2 hover:bg-red-500/20 text-slate-500 hover:text-red-500 rounded-lg transition-all"
                        title="Excluir"
                      >
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/><line x1="10" y1="11" x2="10" y2="17"/><line x1="14" y1="11" x2="14" y2="17"/></svg>
                      </button>
                    )}
                  </div>
                )) : (
                  <div className="p-10 text-center text-slate-500 uppercase text-xs font-bold tracking-widest">Nenhum registro encontrado</div>
                )}
              </div>
            </div>
          </div>
        ) : (
          <div className="max-w-4xl mx-auto bg-slate-900 p-8 rounded-3xl border border-slate-800 shadow-2xl">
            <h2 className="text-3xl font-black text-emerald-500 uppercase tracking-tighter mb-6">{form.uuid ? 'Edição' : 'Novo Protocolo'}</h2>
            
            {errors.length > 0 && (
              <div className="mb-6 p-4 bg-red-500/10 border border-red-500/20 rounded-xl">
                {errors.map((err, i) => <p key={i} className="text-red-400 text-xs font-bold uppercase tracking-tight">• {err}</p>)}
              </div>
            )}

            <form onSubmit={handleSalvar} className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="space-y-4">
                <div className="grid grid-cols-2 gap-4">
                  <input placeholder="Identificador (Ex: CF-88)" type="text" value={form.identificador} onChange={e => setForm({...form, identificador: e.target.value})} className="bg-slate-950 border border-slate-800 p-3 rounded-xl outline-none focus:ring-1 focus:ring-emerald-500 text-sm" />
                  <input placeholder="Artigo Nº" type="text" value={form.artigo} onChange={e => setForm({...form, artigo: e.target.value})} className="bg-slate-950 border border-slate-800 p-3 rounded-xl outline-none focus:ring-1 focus:ring-emerald-500 text-sm" />
                </div>
                <input placeholder="Título da Norma" type="text" value={form.titulo} onChange={e => setForm({...form, titulo: e.target.value})} className="w-full bg-slate-950 border border-slate-800 p-3 rounded-xl outline-none focus:ring-1 focus:ring-emerald-500 text-sm" />
                <input placeholder="Tipo de Norma" type="text" value={form.tipoNorma} onChange={e => setForm({...form, tipoNorma: e.target.value})} className="w-full bg-slate-950 border border-slate-800 p-3 rounded-xl outline-none text-sm" />
                <input placeholder="Status Tramitação" type="text" value={form.statusTramitacao} onChange={e => setForm({...form, statusTramitacao: e.target.value})} className="w-full bg-slate-950 border border-slate-800 p-3 rounded-xl outline-none text-sm" />
              </div>

              <div className="space-y-4">
                <div className="grid grid-cols-2 gap-4">
                  <input placeholder="Risco (0-1)" type="number" step="0.1" value={form.riscoInconstitucionalidade} onChange={e => setForm({...form, riscoInconstitucionalidade: parseFloat(e.target.value)})} className="bg-slate-950 border border-slate-800 p-3 rounded-xl outline-none text-sm" />
                  <select value={form.geraDespesa ? 'true' : 'false'} onChange={e => setForm({...form, geraDespesa: e.target.value === 'true'})} className="bg-slate-950 border border-slate-800 p-3 rounded-xl outline-none text-sm">
                    <option value="false">Gera Despesa: Não</option>
                    <option value="true">Gera Despesa: Sim</option>
                  </select>
                </div>
                <textarea placeholder="Ementa (Resumo)" value={form.ementa} onChange={e => setForm({...form, ementa: e.target.value})} className="w-full bg-slate-950 border border-slate-800 p-3 rounded-xl outline-none h-20 text-sm" />
                <textarea placeholder="Texto Original" value={form.textoOriginal} onChange={e => setForm({...form, textoOriginal: e.target.value})} className="w-full bg-slate-950 border border-slate-800 p-3 rounded-xl outline-none h-24 text-sm font-mono" />
              </div>

              <div className="md:col-span-2">
                <button type="submit" disabled={loading} className="w-full py-4 bg-emerald-600 hover:bg-emerald-500 text-white font-black rounded-2xl transition-all">
                  {loading ? 'SINCRONIZANDO...' : form.uuid ? 'ATUALIZAR' : 'PROTOCOLAR NO REDIS'}
                </button>
              </div>
            </form>
          </div>
        )}
      </main>
    </div>
  );
}

export default App;
// eof

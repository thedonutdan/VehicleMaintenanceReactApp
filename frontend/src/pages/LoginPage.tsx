import { API_BASE } from '../constants'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

function LoginPage() {
    const [loginUsername, setLoginUsername] = useState('')
    const [loginPassword, setLoginPassword] = useState('')
    const [rememberMe, setRememberMe] = useState(false)
    const [loginError, setLoginError] = useState('')
    const [registerUsername, setRegisterUsername] = useState('')
    const [registerPassword, setRegisterPassword] = useState('')
    const [registerError, setRegisterError] = useState('')
    const navigate = useNavigate()

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault()

        const res = await fetch(`${API_BASE}/auth/login`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            credentials: 'include',
            body: JSON.stringify({
                username: loginUsername,
                password: loginPassword,
                rememberMe})
        })

        if (res.ok) {
            navigate('/vehicles')
        } else {
            const msg = await res.text()
            setLoginError(msg)
        }
    }

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault()

        const res = await fetch(`${API_BASE}/auth/register`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            credentials: 'include',
            body: JSON.stringify({
                username: registerUsername,
                password: registerPassword
            })
        })

        if (res.ok) {
            navigate('login')
        } else {
            const msg = await res.text()
            setRegisterError(msg)
        }
    }

    return (
        <section>
            <form onSubmit={handleLogin}>
                <h2>Login</h2>
                {loginError && <p style={{ color: 'red'}}>{loginError}</p>}
                <input value={loginUsername} onChange={e => setLoginUsername(e.target.value)} placeholder="Username" />
                <input value={loginPassword} onChange={e => setLoginPassword(e.target.value)} type="password" />
                <label>
                    <input type="checkbox" checked={rememberMe} onChange={e => setRememberMe(e.target.checked)} />
                    Remember Me
                </label>
                <button type="submit">Login</button>
            </form>
            <form onSubmit={handleRegister}>
                <h2>Register</h2>
                {registerError && <p style={{ color: 'red'}}>{registerError}</p>}
                <input value={registerUsername} onChange={e => setRegisterUsername(e.target.value)} placeholder="Username" />
                <input value={registerPassword} onChange={e => setRegisterPassword(e.target.value)} type="password" />
                <button type="submit">Register</button>
            </form>
        </section>
    )
}

export default LoginPage
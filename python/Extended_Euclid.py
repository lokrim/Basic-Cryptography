def extended_gcd(a, b):
    """
    Returns (g, x, y) such that a*x + b*y = g = gcd(a, b)
    """
    if a == 0:
        return b, 0, 1
    else:
        g, y, x = extended_gcd(b % a, a)
        return g, x - (b // a) * y, y

def mod_inverse(a, m):
    """
    Returns modular multiplicative inverse of a under modulo m.
    Raises exception if inverse does not exist.
    """
    g, x, y = extended_gcd(a, m)
    
    if g != 1:
        raise ValueError("Modular inverse does not exist (a and m are not coprime)")
    else:
        # Result x might be negative, so we convert it to positive
        return (x % m + m) % m

# --- Main Execution ---
if __name__ == "__main__":
    a = 11
    m = 26
    
    try:
        inv = mod_inverse(a, m)
        print(f"Modular Inverse of {a} mod {m} is: {inv}")
        print(f"Check: ({a} * {inv}) % {m} = {(a * inv) % m}")
    except ValueError as e:
        print(e)
import math

def mod_inverse(a, m):
    """Finds modular multiplicative inverse of a under modulo m."""
    for x in range(1, m):
        if (a * x) % m == 1:
            return x
    return None  # Should not happen if a and m are coprime

def affine_encrypt(text, key_a, key_b):
    """Encrypts text using Affine Cipher."""
    # Check validity of 'a'
    if math.gcd(key_a, 26) != 1:
        raise ValueError("Key 'a' must be coprime to 26.")
        
    result = ""
    for char in text:
        if char.isupper():
            # Formula: (ax + b) % 26
            x = ord(char) - 65
            result += chr(((key_a * x + key_b) % 26) + 65)
        elif char.islower():
            x = ord(char) - 97
            result += chr(((key_a * x + key_b) % 26) + 97)
        else:
            result += char
    return result

def affine_decrypt(text, key_a, key_b):
    """Decrypts text using Affine Cipher."""
    if math.gcd(key_a, 26) != 1:
        raise ValueError("Key 'a' must be coprime to 26.")
        
    result = ""
    # Calculate a^-1 (modular inverse)
    a_inv = mod_inverse(key_a, 26)
    
    for char in text:
        if char.isupper():
            # Formula: a^-1 * (x - b) % 26
            x = ord(char) - 65
            result += chr((a_inv * (x - key_b)) % 26 + 65)
        elif char.islower():
            x = ord(char) - 97
            result += chr((a_inv * (x - key_b)) % 26 + 97)
        else:
            result += char
    return result

# --- Main Execution ---
if __name__ == "__main__":
    msg = "AFFINE CIPHER"
    a, b = 5, 8  # 'a' must be coprime to 26
    
    encrypted = affine_encrypt(msg, a, b)
    decrypted = affine_decrypt(encrypted, a, b)
    
    print(f"Original:  {msg}")
    print(f"Encrypted: {encrypted}")
    print(f"Decrypted: {decrypted}")
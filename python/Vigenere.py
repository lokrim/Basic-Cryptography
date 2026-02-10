def vigenere_encrypt(text, key):
    """Encrypts text using Vigenère Cipher."""
    result = ""
    key_index = 0
    key = key.upper()
    
    for char in text:
        if char.isalpha():
            # Determine shift from key character
            shift = ord(key[key_index % len(key)]) - 65
            
            if char.isupper():
                result += chr((ord(char) + shift - 65) % 26 + 65)
            else:
                result += chr((ord(char) + shift - 97) % 26 + 97)
            
            key_index += 1
        else:
            result += char
            
    return result

def vigenere_decrypt(text, key):
    """Decrypts text using Vigenère Cipher."""
    result = ""
    key_index = 0
    key = key.upper()
    
    for char in text:
        if char.isalpha():
            # Determine shift from key character
            shift = ord(key[key_index % len(key)]) - 65
            
            if char.isupper():
                # Subtract shift for decryption
                result += chr((ord(char) - shift - 65 + 26) % 26 + 65)
            else:
                result += chr((ord(char) - shift - 97 + 26) % 26 + 97)
            
            key_index += 1
        else:
            result += char
            
    return result

# --- Main Execution ---
if __name__ == "__main__":
    text = "HELLO WORLD"
    key = "KEY"
    
    encrypted = vigenere_encrypt(text, key)
    decrypted = vigenere_decrypt(encrypted, key)
    
    print(f"Original:  {text}")
    print(f"Key:       {key}")
    print(f"Encrypted: {encrypted}")
    print(f"Decrypted: {decrypted}")
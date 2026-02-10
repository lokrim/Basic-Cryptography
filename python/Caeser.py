def caesar_encrypt(text, shift):
    """Encrypts text using Caesar Cipher."""
    result = ""
    
    # Traverse text
    for i in range(len(text)):
        char = text[i]
        
        # Encrypt Uppercase characters
        if char.isupper():
            # ord('A') is 65. We subtract it to get 0-25 range, shift, modulo, then add back.
            result += chr((ord(char) + shift - 65) % 26 + 65)
            
        # Encrypt Lowercase characters
        elif char.islower():
            # ord('a') is 97
            result += chr((ord(char) + shift - 97) % 26 + 97)
            
        # Leave non-alphabetic characters (numbers, symbols) as is
        else:
            result += char
            
    return result

def caesar_decrypt(text, shift):
    """Decrypts text using Caesar Cipher."""
    # Decryption is simply encryption with a negative shift
    # We use (26 - shift) to ensure we handle the wrap-around correctly in positive modulo
    return caesar_encrypt(text, 26 - (shift % 26))

# --- Main Execution ---
if __name__ == "__main__":
    text = "Hello, World!"
    shift = 3
    
    encrypted = caesar_encrypt(text, shift)
    decrypted = caesar_decrypt(encrypted, shift)
    
    print(f"Original:  {text}")
    print(f"Encrypted: {encrypted}")
    print(f"Decrypted: {decrypted}")
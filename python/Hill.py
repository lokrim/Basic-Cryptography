import sys

def mod_inverse(a, m):
    """Finds modular multiplicative inverse of a under modulo m."""
    a = a % m
    for x in range(1, m):
        if (a * x) % m == 1:
            return x
    return None

def get_key_matrix_inverse(key_matrix):
    """Calculates the inverse of a 2x2 matrix modulo 26."""
    # Determinant = ad - bc
    det = key_matrix[0][0] * key_matrix[1][1] - key_matrix[0][1] * key_matrix[1][0]
    det = det % 26
    
    det_inv = mod_inverse(det, 26)
    if det_inv is None:
        raise ValueError("Key matrix is not invertible modulo 26.")
        
    # Inverse Matrix formula for 2x2:
    # [ d  -b ]
    # [ -c  a ]  * det_inv
    
    # Swap a and d, negate b and c
    inv_matrix = [
        [(key_matrix[1][1] * det_inv) % 26, (-key_matrix[0][1] * det_inv) % 26],
        [(-key_matrix[1][0] * det_inv) % 26, (key_matrix[0][0] * det_inv) % 26]
    ]
    return inv_matrix

def hill_cipher(text, key_matrix, mode='encrypt'):
    text = text.replace(" ", "").upper()
    # Padding if length is odd
    if len(text) % 2 != 0:
        text += "X"
        
    n = 2 # Size of the matrix (2x2)
    result = ""
    
    # Determine which matrix to use
    if mode == 'encrypt':
        matrix = key_matrix
    else:
        matrix = get_key_matrix_inverse(key_matrix)
        
    # Process text in blocks of 2
    for i in range(0, len(text), n):
        # Convert char block to vector [x, y]
        vector = [ord(text[i]) - 65, ord(text[i+1]) - 65]
        
        # Matrix Multiplication: (Matrix * Vector) % 26
        # C1 = (K00*P0 + K01*P1) % 26
        # C2 = (K10*P0 + K11*P1) % 26
        enc_vector = [
            (matrix[0][0] * vector[0] + matrix[0][1] * vector[1]) % 26,
            (matrix[1][0] * vector[0] + matrix[1][1] * vector[1]) % 26
        ]
        
        result += chr(enc_vector[0] + 65) + chr(enc_vector[1] + 65)
        
    return result

# --- Main Execution ---
if __name__ == "__main__":
    # Key matrix [[3, 3], [2, 5]]
    # Determinant = 15 - 6 = 9 (Coprime to 26, so it works)
    key = [[3, 3], [2, 5]]
    message = "HELP"
    
    try:
        encrypted = hill_cipher(message, key, mode='encrypt')
        decrypted = hill_cipher(encrypted, key, mode='decrypt')
        
        print(f"Original:  {message}")
        print(f"Encrypted: {encrypted}")
        print(f"Decrypted: {decrypted}")
    except ValueError as e:
        print(e)
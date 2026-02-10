def create_matrix(key):
    """Generates the 5x5 key matrix."""
    key = key.upper().replace("J", "I")
    matrix = []
    seen = set()
    
    # Add key characters first
    for char in key:
        if char not in seen and char.isalpha():
            seen.add(char)
            matrix.append(char)
    
    # Add remaining alphabet
    for char in "ABCDEFGHIKLMNOPQRSTUVWXYZ":
        if char not in seen:
            seen.add(char)
            matrix.append(char)
            
    # Convert list to 5x5 grid
    return [matrix[i:i+5] for i in range(0, 25, 5)]

def find_position(matrix, char):
    """Finds row and column of a character."""
    for r, row in enumerate(matrix):
        if char in row:
            return r, row.index(char)
    return None

def preprocess_text(text):
    """Splits text into pairs, handling duplicates and odd lengths."""
    text = text.upper().replace("J", "I").replace(" ", "")
    # Remove non-alpha
    text = "".join(filter(str.isalpha, text))
    
    pairs = []
    i = 0
    while i < len(text):
        a = text[i]
        # Check if we are at the last char or if next char is same
        if i + 1 == len(text):
            pairs.append(a + 'X')
            i += 1
        elif a == text[i+1]:
            pairs.append(a + 'X')
            i += 1
        else:
            pairs.append(a + text[i+1])
            i += 2
    return pairs

def playfair_cipher(text, key, mode='encrypt'):
    matrix = create_matrix(key)
    pairs = preprocess_text(text) if mode == 'encrypt' else [text[i:i+2] for i in range(0, len(text), 2)]
    result = []
    
    shift = 1 if mode == 'encrypt' else -1
    
    for a, b in pairs:
        row1, col1 = find_position(matrix, a)
        row2, col2 = find_position(matrix, b)
        
        if row1 == row2:  # Same Row
            result.append(matrix[row1][(col1 + shift) % 5])
            result.append(matrix[row2][(col2 + shift) % 5])
        elif col1 == col2:  # Same Column
            result.append(matrix[(row1 + shift) % 5][col1])
            result.append(matrix[(row2 + shift) % 5][col2])
        else:  # Rectangle
            result.append(matrix[row1][col2])
            result.append(matrix[row2][col1])
            
    return "".join(result)

# --- Main Execution ---
if __name__ == "__main__":
    key = "MONARCHY"
    text = "INSTRUMENTS"
    
    encrypted = playfair_cipher(text, key, mode='encrypt')
    decrypted = playfair_cipher(encrypted, key, mode='decrypt')
    
    print(f"Key:       {key}")
    print(f"Original:  {text}")
    print(f"Encrypted: {encrypted}")
    print(f"Decrypted: {decrypted}") 
    # Note: 'I' might replace 'J' in decrypted text naturally